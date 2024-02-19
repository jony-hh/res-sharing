package com.jony.enums;


/**
 * @author jony
 * @date ：2023-03-10 22:46
 * @description ：Redis 常用缓存的 key
 */
public enum RedisKeyEnum {
    /**
     * Redis 常用缓存的 key
     */
    KEY_PERMISSION_URL_LIST("PERMISSION_URL_LIST", "权限URL集合"),
    GROUP_AUTH_TOKEN("GROUP_AUTH_TOKEN:", "AuthToken分组"),
    GROUP_REMEMBER_ME_TOKEN("GROUP_REMEMBER_ME_TOKEN:", "RememberMeToken分组"),
    GROUP_CURRENT_ONLINE_USER("GROUP_CURRENT_ONLINE_USER:", "在线用户分组"),

    ;

    private final String key;
    private final String des;

    RedisKeyEnum(String key, String des) {
        this.key = key;
        this.des = des;
    }

    public String getKey() {
        return key;
    }

    public String getDes() {
        return des;
    }
}
