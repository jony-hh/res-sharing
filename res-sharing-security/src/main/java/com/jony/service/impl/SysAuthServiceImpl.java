package com.jony.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yitter.idgen.YitIdHelper;
import com.jony.convert.SysUserConvert;
import com.jony.entity.SysAuth;
import com.jony.entity.SysUser;
import com.jony.enums.UserEnum;
import com.jony.exception.BusinessException;
import com.jony.exception.ServerException;
import com.jony.mapper.SysAuthMapper;
import com.jony.mapper.SysUserMapper;
import com.jony.security.dto.UserRegisterDto;
import com.jony.security.token.EmailAuthenticationToken;
import com.jony.security.token.LocalAuthenticationToken;
import com.jony.security.token.PhoneAuthenticationToken;
import com.jony.security.vo.UserInfoVo;
import com.jony.service.SysAuthService;
import com.jony.utils.TokenUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;


/**
 * sysAuth表-服务实现类
 *
 * @author jony
 * @since 2024-02-07 13:06:29
 */
@Service
@RequiredArgsConstructor
public class SysAuthServiceImpl extends ServiceImpl<SysAuthMapper, SysAuth> implements SysAuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final SysAuthMapper sysAuthMapper;
    private final SysUserMapper sysUserMapper;
    @Value("${shuishu.encrypt.salt}")
    private String salt;

    @Override
    public String login(String name, String pwd, UserEnum.AuthType authType, Boolean isRememberMe, HttpServletResponse response) {
        Authentication authentication = null;
        if (UserEnum.AuthType.LOCAL.equals(authType)) {
            authentication = authenticationManager.authenticate(new LocalAuthenticationToken(name, pwd));
        } else if (UserEnum.AuthType.EMAIL.equals(authType)) {
            authentication = authenticationManager.authenticate(new EmailAuthenticationToken(name, pwd));
        } else if (UserEnum.AuthType.PHONE.equals(authType)) {
            authentication = authenticationManager.authenticate(new PhoneAuthenticationToken(name, pwd));
        } else {
            throw new BusinessException("不支持的登录方式");
        }
        UserInfoVo userInfoVO = (UserInfoVo) authentication.getPrincipal();

        // 生成token，或者在provider中生成
        String token = tokenUtils.setUserInfoVo(userInfoVO, isRememberMe, response);
        if (userInfoVO != null && token == null) {
            throw new BusinessException(401, "登录失败");
        }
        return token;
    }


    @Override
    @Transactional
    public boolean register(UserRegisterDto userRegisterDto, String registerType, HttpServletResponse response) {
        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String confirmPassword = userRegisterDto.getConfirmPassword();
        String encrypt = DigestUtils.md5DigestAsHex((salt + password).getBytes());

        if (!password.equals(confirmPassword)) {
            throw new ServerException("密码不一样");
        }
        if (UserEnum.AuthType.LOCAL.getType().equals(registerType)) {
            SysAuth sysAuth = new SysAuth();
            sysAuth.setId(YitIdHelper.nextId());
            sysAuth.setUserId(YitIdHelper.nextId());
            sysAuth.setIdentifier(username);
            sysAuth.setCertificate(encrypt);
            sysAuth.setAuthType(UserEnum.AuthType.LOCAL.getType());
            SysUser sysUser = SysUserConvert.INSTANCE.toResDocument(sysAuth);
            int result = sysAuthMapper.insert(sysAuth);
            int result2 = sysUserMapper.insert(sysUser);
            return result > 0 && result2>0;
        }
        return true;
    }


}

