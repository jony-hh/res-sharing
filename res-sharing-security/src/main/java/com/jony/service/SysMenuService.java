package com.jony.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jony.entity.SysMenu;
import com.jony.security.dto.PermissionCacheDto;

import java.util.List;
import java.util.Set;


/**
 * sysMenu表-服务接口
 *
 * @author jony
 * @since 2024-02-07 14:28:50
 */
public interface SysMenuService extends IService<SysMenu> {

    Set<String> findPermissionInfo();

    Set<String> findPermissionInfoByRoleIdList(List<Long> roleIdList);

    List<PermissionCacheDto> findCachePermissionList();
}


