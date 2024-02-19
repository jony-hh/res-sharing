package com.jony.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jony.entity.SysRole;
import com.jony.entity.SysRoleMenu;
import com.jony.mapper.SysRoleMapper;
import com.jony.mapper.SysRoleMenuMapper;
import com.jony.security.dto.PermissionCacheDto;
import com.jony.security.dto.RoleCacheDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.jony.entity.SysMenu;
import com.jony.mapper.SysMenuMapper;
import com.jony.service.SysMenuService;

import java.util.*;
import java.util.stream.Collectors;


/**
 * sysMenu表-服务实现类
 *
 * @author jony
 * @since 2024-02-07 14:28:50
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysRoleMenuMapper roleMenuMapper;
    private final SysRoleMapper roleMapper;
    private final SysMenuMapper menuMapper;


    /**
     * 查询所有权限
     *
     * @return List<SysMenu>
     */
    public Set<String> findPermissionInfo(){
        List<SysMenu> menuList = this.list();
        Set<String> authorityList = menuList.stream()
                .map(SysMenu::getAuthority)
                .collect(Collectors.toSet());

        // 用户权限列表
        Set<String> authoritySet = new HashSet<>();
        for (String authority : authorityList) {
            if (StrUtil.isBlank(authority)) {
                continue;
            }
            authoritySet.addAll(Arrays.asList(authority.trim().split(",")));
        }
        return authoritySet;
    }

    /**
     * 通过角色列表查询所有权限
     * @param roleIdList
     * @return List<SysMenu>
     */
    @Override
    public Set<String> findPermissionInfoByRoleIdList(List<Long> roleIdList) {
        List<List<SysRoleMenu>> allData = new ArrayList<>();
        // 一个role可能对应多个menu
        for (Long roleId : roleIdList) {
            LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysRoleMenu::getRoleId, roleId);
            List<SysRoleMenu> sysRoleMenus = roleMenuMapper.selectList(wrapper);
            if (sysRoleMenus != null) {
                allData.add(sysRoleMenus);
            }
        }

        // 【Flatten扁平化、distinct根据id去重】重写hashcode与equals
        List<SysRoleMenu> roleMenus = allData.stream()
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());

        // 得到所有的menuid
        Set<Long> menuIds = roleMenus.stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toSet());

        List<SysMenu> menus = new ArrayList<>();
        for (Long menuId : menuIds) {
            SysMenu sysMenu = menuMapper.selectById(menuId);
            if (sysMenu != null) {
                menus.add(sysMenu);
            }
        }

        Set<String> authorityList = menus.stream()
                .map(SysMenu::getAuthority)
                .collect(Collectors.toSet());

        // 用户权限列表
        Set<String> authoritySet = new HashSet<>();
        for (String authority : authorityList) {
            if (StrUtil.isBlank(authority)) {
                continue;
            }
            authoritySet.addAll(Arrays.asList(authority.trim().split(",")));
        }
        return authoritySet;
    }

    /**
     * 查询所有权限菜单 和 拥有该权限的角色列表
     *
     * @return 权限菜单列表
     */
    @Override
    public List<PermissionCacheDto> findCachePermissionList() {
        LambdaQueryWrapper<SysMenu> permissionQueryWrapper = new LambdaQueryWrapper<>();
        List<SysMenu> permissions = menuMapper.selectList(permissionQueryWrapper);

        return permissions.stream()
                .map(permission -> {
                    // 查询拥有该权限的角色列表
                    List<RoleCacheDto> roleCacheDtoList = findRolesByPermissionId(permission.getId());

                    // 将查询结果封装为PermissionCacheDto对象
                    PermissionCacheDto permissionCacheDto = new PermissionCacheDto();
                    permissionCacheDto.setPermissionCode(permission.getAuthority());
                    permissionCacheDto.setPermissionUrl(permission.getUrl());
                    permissionCacheDto.setIsNeedAuthorization(Boolean.parseBoolean(permission.getIsNeedAuthorization()));
                    permissionCacheDto.setRoleCacheDtoList(roleCacheDtoList);

                    return permissionCacheDto;
                })
                .collect(Collectors.toList());
    }

    /**
     * 根据权限ID查询拥有该权限的角色列表
     *
     * @param permissionId 权限ID
     * @return 拥有该权限的角色列表
     */
    private List<RoleCacheDto> findRolesByPermissionId(Long permissionId) {
        LambdaQueryWrapper<SysRoleMenu> rolePermissionQueryWrapper = new LambdaQueryWrapper<>();
        rolePermissionQueryWrapper.eq(SysRoleMenu::getMenuId, permissionId);

        List<SysRoleMenu> rolePermissions = roleMenuMapper.selectList(rolePermissionQueryWrapper);

        List<Long> roleIds = rolePermissions.stream()
                .map(SysRoleMenu::getRoleId)
                .collect(Collectors.toList());

        // LambdaQueryWrapper<SysRole> roleQueryWrapper = new LambdaQueryWrapper<>();
        // roleQueryWrapper.in(SysRole::getId, roleIds);
        // List<SysRole> sysRoles = roleMapper.selectList(roleQueryWrapper);

        List<SysRole> sysRoleList = new ArrayList<>();
        for (Long roleId : roleIds) {
            SysRole sysRole = roleMapper.selectById(roleId);
            sysRoleList.add(sysRole);
        }

        List<RoleCacheDto> roleCacheDtoList = sysRoleList.stream()
                .map(role -> new RoleCacheDto(role.getRoleCode()))
                .collect(Collectors.toList());

        return roleCacheDtoList;
    }

}

