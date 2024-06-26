package com.jony.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jony.entity.SysAuth;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
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
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
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
        // 设置角色
        List<SysRole> roleInfoList = roleMapper.findRoleInfoByUserId(userInfoVO.getId());
        if (!ObjectUtils.isEmpty(roleInfoList)) {
            userInfoVO.setRoleInfoList(roleInfoList);

            // 设置权限
            // 管理员直接返回所有权限
            for (SysRole role : roleInfoList) {
                if (role.getRoleCode().equals("sys_admin")) {
                    Set<String> permissionInfoList = menuService.findPermissionInfo();
                    if (!ObjectUtils.isEmpty(permissionInfoList)) {
                        userInfoVO.setAuthoritySet(permissionInfoList);
                        return userInfoVO;
                    }
                }
            }

            // 不是管理员则根据【角色id】查询权限
            List<Long> roleIds = roleInfoList.stream().map(SysRole::getId).collect(Collectors.toList());
            Set<String> permissionInfoList = menuService.findPermissionInfoByRoleIdList(roleIds);
            if (!ObjectUtils.isEmpty(permissionInfoList)) {
                userInfoVO.setAuthoritySet(permissionInfoList);
            }
        }

        return userInfoVO;
    }

    @Override
    public List<HashMap<String, Object>> getList(String query, Integer page, Integer size) {
        Page<SysUser> sysUserPage = new Page<>();
        sysUserPage.setCurrent(page);
        sysUserPage.setSize(size);
        if (query == null) {
            IPage<SysUser> pageInfo = userMapper.selectPage(sysUserPage, null);

            HashMap<String, Object> hashMap1 = new HashMap<>();
            hashMap1.put("total", pageInfo.getTotal());

            HashMap<String, Object> hashMap2 = new HashMap<>();
            hashMap2.put("list", pageInfo.getRecords());

            List<HashMap<String, Object>> userList = new ArrayList<>();
            userList.add(hashMap1);
            userList.add(hashMap2);
            return userList;
        }
        QueryWrapper<SysUser> wrapper = Wrappers.query();
        wrapper.like("username", query);
        IPage<SysUser> pageInfo = userMapper.selectPage(sysUserPage, wrapper);


        HashMap<String, Object> hashMap1 = new HashMap<>();
        hashMap1.put("total", pageInfo.getTotal());

        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("list", pageInfo.getRecords());

        List<HashMap<String, Object>> userList = new ArrayList<>();
        userList.add(hashMap1);
        userList.add(hashMap2);

        return userList;
    }

}

