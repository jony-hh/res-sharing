package com.jony.security.cache;


import com.jony.security.dto.PermissionCacheDto;

import java.util.List;

/**
 * @author jony
 * @date ：2023-03-10 22:31
 * @description ：资源管理：角色、权限
 */
public interface ResourceService {
    /**
     * 查询所有权限集合
     *
     * @return 权限集合
     */
    List<PermissionCacheDto> findCachePermissionList();

}
