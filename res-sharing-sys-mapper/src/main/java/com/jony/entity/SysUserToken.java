package com.jony.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * sysUserToken表-实体类
 *
 * @author jony
 * @since 2024-02-07 14:28:51
 */
@TableName("sys_user_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserToken implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * accessToken
     */
    private String accessToken;

    /**
     * accessToken 过期时间
     */
    private Timestamp accessTokenExpire;

    /**
     * refreshToken
     */
    private String refreshToken;

    /**
     * refreshToken 过期时间
     */
    private Timestamp refreshTokenExpire;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 创建时间
     */
    private Timestamp createTime;


}

