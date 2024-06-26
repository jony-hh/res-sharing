package com.jony.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yitter.idgen.YitIdHelper;
import com.jony.dto.*;
import com.jony.entity.SysAuth;
import com.jony.entity.SysRole;
import com.jony.entity.SysUser;
import com.jony.entity.SysUserRole;
import com.jony.enums.RedisKeyEnum;
import com.jony.enums.ThumbOrStarStatusEum;
import com.jony.exception.ErrorCode;
import com.jony.exception.ServerException;
import com.jony.mapper.SysAuthMapper;
import com.jony.mapper.SysRoleMapper;
import com.jony.mapper.SysUserMapper;
import com.jony.mapper.SysUserRoleMapper;
import com.jony.service.UserService;
import com.jony.utils.PasswordUtils;
import com.jony.utils.RedisStarUtil;
import com.jony.utils.RedisThumbUtil;
import com.jony.utils.RedisUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements UserService {

    private final SysUserMapper sysUserMapper;
    private final SysAuthMapper sysAuthMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final RedisUtils redisUtils;
    private final RedisThumbUtil redisThumbUtil;
    private final RedisStarUtil redisStarUtil;

    @Value("${shuishu.token.auth-token}")
    private String ymlAuthToken;

    @Value("${shuishu.token.remember-me-token}")
    private String ymlRememberMeToken;

    @Value("${shuishu.token.secret}")
    private String ymlSecret;

    @Value("${shuishu.token.remember-me-token-expire-time}")
    private int ymlRememberMeTokenExpireTime;

    @Value("${shuishu.token.expire-time}")
    private int ymlExpireTime;

    /**
     * feat: 盐值，混淆密码
     */
    @Value("${shuishu.encrypt.salt}")
    private String salt;

    @Override
    public SysUser getLoginUser(HttpServletRequest request) {
        // 当前登录用户
        SysUser loginUser = null;
        String headerAuthToken = request.getHeader(ymlAuthToken);
        if (headerAuthToken != null) {
            Object userInfoObjForAuthToken = redisUtils.strGet(RedisKeyEnum.GROUP_AUTH_TOKEN.getKey() + headerAuthToken);
            loginUser = (SysUser) userInfoObjForAuthToken;
        } else {
            // 请求头没有 AuthToken 只有 RememberMeToken 也尽最大可能的尝试获取用户信息，创建新的 token，进行缓存
            String headerRememberMeToken = request.getHeader(ymlRememberMeToken);
            if (org.springframework.util.StringUtils.hasText(headerRememberMeToken)) {
                // TODO 使用【刷新token】获取登录用户
            }
        }
        return loginUser;
    }

    @Override
    public boolean localRegister(UserRegisterDTO userRegisterDTO) {
        // 1. 判断密码是否一样
        String password = userRegisterDTO.getPassword();
        String confirmPassword = userRegisterDTO.getConfirmPassword();
        if (!password.equals(confirmPassword)) {
            return false;
        }
        // 2、判断是否被注册
        LambdaQueryWrapper<SysAuth> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysAuth::getIdentifier,userRegisterDTO.getUsername());
        SysAuth sysAuth = sysAuthMapper.selectOne(lambdaQueryWrapper);
        if (sysAuth!=null) {
            return false;
        }
        // 3、保存用户
        String encryptPassword = DigestUtils.md5DigestAsHex((salt + password).getBytes());
        SysAuth newAuth = new SysAuth();
        newAuth.setUserId(YitIdHelper.nextId());
        newAuth.setAuthType(userRegisterDTO.getAuthType());
        newAuth.setIdentifier(userRegisterDTO.getUsername());
        newAuth.setCertificate(encryptPassword);
        int result = sysAuthMapper.insert(newAuth);

        return result == 1;
    }

    @Override
    public List<String> getUserRole(Long id) {
        // 构建查询条件
        LambdaQueryWrapper<SysUserRole> userRoleQueryWrapper = new LambdaQueryWrapper<>();
        userRoleQueryWrapper.eq(SysUserRole::getUserId, id);
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(userRoleQueryWrapper);

        // 提取角色ID
        List<Long> roleIds = userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());

        // 查询角色信息
        LambdaQueryWrapper<SysRole> roleQueryWrapper = new LambdaQueryWrapper<>();
        roleQueryWrapper.in(SysRole::getId, roleIds);
        List<SysRole> sysRoles = sysRoleMapper.selectList(roleQueryWrapper);
        List<String> roleCodes = sysRoles.stream().map(SysRole::getRoleCode).collect(Collectors.toList());
        return roleCodes;
    }

    @Override
    public boolean authenticate(UserLoginDTO userLoginDTO, HttpServletResponse response) {

        // 1. 加密
        String password = userLoginDTO.getPassword();
        String encryptPassword = DigestUtils.md5DigestAsHex((salt + password).getBytes());
        // 2、站内账号查sys_user表
        LambdaQueryWrapper<SysUser> sysUserWrapper = new LambdaQueryWrapper<>();
        sysUserWrapper.eq(SysUser::getUsername, userLoginDTO.getUsername());
        sysUserWrapper.eq(SysUser::getPassword, encryptPassword);
        SysUser sysUser = sysUserMapper.selectOne(sysUserWrapper);
        if (sysUser == null) {
            throw new ServerException(ErrorCode.OPERATION_ERROR);
        }
        // 3、验证成功对token进行缓存
        String authToken = PasswordUtils.encrypt(ymlSecret, UUID.randomUUID().toString().replace("-", ""));
        if (StringUtils.hasText(authToken)) {
            redisUtils.strSet(RedisKeyEnum.GROUP_AUTH_TOKEN.getKey() + authToken, sysUser, ymlExpireTime);
            response.addHeader("Access-Control-Expose-Headers", ymlAuthToken);
            response.setHeader(ymlAuthToken, authToken);
            if (Boolean.TRUE.equals(userLoginDTO.getIsRememberMe())) {
                String rememberMeToken = PasswordUtils.encrypt(ymlSecret, UUID.randomUUID().toString().replace("-", ""));
                redisUtils.strSet(RedisKeyEnum.GROUP_REMEMBER_ME_TOKEN.getKey() + rememberMeToken, sysUser, ymlRememberMeTokenExpireTime);
                response.setHeader(ymlRememberMeToken, rememberMeToken);
            }
        }
        return true;
    }

    @Override
    public boolean logout(HttpServletRequest request) {
        String headerAuthToken = request.getHeader(ymlAuthToken);
        if (headerAuthToken != null) {
            redisUtils.del(RedisKeyEnum.GROUP_AUTH_TOKEN.getKey() + headerAuthToken);
            return true;
        }
        return false;
    }

    @Override
    public ThumbOrStarStatusEum thumb(HttpServletRequest request, UserThumbDTO userThumbDTO) {
        // 判断用户是否有效登录
        SysUser loginUser = getLoginUser(request);
        if (loginUser == null) {
            return ThumbOrStarStatusEum.ERROR;
        }
        ThumbOrStarStatusEum thumbOrStarStatusEum = redisThumbUtil.likeStatus(userThumbDTO);
        return thumbOrStarStatusEum;
    }

    @Override
    public ThumbOrStarStatusEum star(HttpServletRequest request, UserStarDTO userStarDTO) {
        // 判断用户是否有效登录
        SysUser loginUser = getLoginUser(request);
        if (loginUser == null) {
            return ThumbOrStarStatusEum.ERROR;
        }
        ThumbOrStarStatusEum thumbOrStarStatusEum = redisStarUtil.starStatus(userStarDTO);
        return thumbOrStarStatusEum;
    }

    @Override
    public boolean updateUser(UserUpdateDTO userUpdateDTO) {
        SysUser user = this.getById(userUpdateDTO.getId());
        user.setCompany(userUpdateDTO.getCompany());
        user.setNickname(userUpdateDTO.getNickname());
        user.setProfession(userUpdateDTO.getProfession());
        user.setInterests(userUpdateDTO.getInterests());
        user.setIntroduction(userUpdateDTO.getIntroduction());
        boolean b = this.updateById(user);
        return b;
    }
}
