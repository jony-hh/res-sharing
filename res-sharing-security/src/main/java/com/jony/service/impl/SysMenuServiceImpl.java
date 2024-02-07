package com.jony.service.impl;

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

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public List<SysMenu> findPermissionInfoByRoleIdList(List<Long> roleIdList) {
        List<List<SysRoleMenu>> allData = new ArrayList<>();
        for (Long roleId : roleIdList) {
            LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysRoleMenu::getRoleId, roleId);
            List<SysRoleMenu> sysRoleMenus = roleMenuMapper.selectList(wrapper);
            allData.add(sysRoleMenus);
        }

        List<SysRoleMenu> roleMenus = allData.stream()
                .flatMap(List::stream)  // Flatten the List<List<SysRoleMenu>> to List<SysRoleMenu>
                .distinct()             // Distinct based on equals and hashCode of SysRoleMenu
                .collect(Collectors.toList());
        List<Long> menuIds = roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());

        List<SysMenu> menus = new ArrayList<>();
        for (Long menuId : menuIds) {
            SysMenu sysMenu = menuMapper.selectById(menuId);
            menus.add(sysMenu);
        }

        return menus;

    }

    /**
     * 查询所有权限菜单及其拥有的角色列表
     *
     * @return 权限菜单列表
     */
    @Override
    public List<PermissionCacheDto> findCachePermissionList() {
        LambdaQueryWrapper<SysMenu> permissionQueryWrapper = Wrappers.lambdaQuery();
        List<SysMenu> permissions = menuMapper.selectList(permissionQueryWrapper);

        return permissions.stream()
                .map(permission -> {
                    // 查询拥有该权限的角色列表
                    List<SysRole> roles = findRolesByPermissionId(permission.getId());

                    // 将查询结果封装为PermissionCacheDto对象
                    PermissionCacheDto permissionCacheDto = new PermissionCacheDto();
                    permissionCacheDto.setPermissionCode(permission.getAuthority());
                    permissionCacheDto.setPermissionUrl(permission.getUrl());
                    permissionCacheDto.setIsNeedAuthorization(Boolean.valueOf(permission.getIsNeedAuthorization()));
                    permissionCacheDto.setRoleCacheDtoList(roles.stream()
                            .map(role -> new RoleCacheDto(role.getRoleCode()))
                            .collect(Collectors.toList()));

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
    private List<SysRole> findRolesByPermissionId(Long permissionId) {
        LambdaQueryWrapper<SysRoleMenu> rolePermissionQueryWrapper = new LambdaQueryWrapper<>();
        rolePermissionQueryWrapper.eq(SysRoleMenu::getMenuId, permissionId);

        List<SysRoleMenu> rolePermissions = roleMenuMapper.selectList(rolePermissionQueryWrapper);

        List<Long> roleIds = rolePermissions.stream()
                .map(SysRoleMenu::getRoleId)
                .collect(Collectors.toList());

        LambdaQueryWrapper<SysRole> roleQueryWrapper = Wrappers.lambdaQuery();
        roleQueryWrapper.in(SysRole::getId, roleIds);
        return roleMapper.selectList(roleQueryWrapper);
    }

}

