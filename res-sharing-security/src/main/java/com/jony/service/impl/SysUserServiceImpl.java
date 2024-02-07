package com.jony.service.impl;


import com.jony.entity.SysAuth;
import com.jony.entity.SysMenu;
import com.jony.entity.SysRole;
import com.jony.entity.SysUser;
import com.jony.mapper.SysAuthMapper;
import com.jony.mapper.SysRoleMapper;
import com.jony.mapper.SysUserMapper;
import com.jony.security.vo.UserInfoVo;
import com.jony.service.SysMenuService;
import com.jony.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * sysUser表-服务实现类
 *
 * @author jony
 * @since 2024-02-07 14:32:03
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {
    private final SysAuthMapper authMapper;
    private final SysUserMapper userMapper;
    private final SysRoleMapper roleMapper;
    private final SysMenuService menuService;

    @Override
    public UserInfoVo findByUserAuthIdentifier(String userAuthIdentifier, String authType) {
        SysAuth userAuth = authMapper.findByUserAuthIdentifierAndAndUserAuthType(userAuthIdentifier, authType);
        if (userAuth == null) {
            return null;
        }
        SysUser user = userMapper.selectById(userAuth.getUserId());
        if (user == null) {
            return null;
        }

        UserInfoVo userInfoVO = new UserInfoVo();
        BeanUtils.copyProperties(userAuth, userInfoVO);
        BeanUtils.copyProperties(user, userInfoVO);

        // 角色
        List<SysRole> roleInfoList = roleMapper.findRoleInfoByUserId(userInfoVO.getId());
        if (!ObjectUtils.isEmpty(roleInfoList)) {
            userInfoVO.setRoleInfoList(roleInfoList);
            // 权限
            List<Long> roleIds = roleInfoList.stream().map(SysRole::getId).collect(Collectors.toList());
            List<SysMenu> permissionInfoList = menuService.findPermissionInfoByRoleIdList(roleIds);
            if (!ObjectUtils.isEmpty(permissionInfoList)) {
                userInfoVO.setPermissionInfoList(permissionInfoList);
            }
        }

        return userInfoVO;
    }

}

