package com.jony.security.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author jony
 * @date ：2023-03-10 23:22
 * @description ：缓存权限集合
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionCacheDto {
    private String permissionCode;

    private String permissionUrl;

    private Boolean isNeedAuthorization;

    private List<RoleCacheDto> roleCacheDtoList;

}
