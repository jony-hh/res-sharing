package com.jony.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * userLoginLog表-实体类
 *
 * @author jony
 * @since 2024-02-07 14:28:51
 */
@TableName("user_login_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginLog implements Serializable {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 用户uid
     */
    private Long userId;

    /**
     * 登录方式 第三方/邮箱/手机等
     */
    private Long type;

    /**
     * 操作类型 1登陆成功  2登出成功 3登录失败 4登出失败
     */
    private Long command;

    /**
     * 客户端版本号
     */
    private String version;

    /**
     * 客户端
     */
    private String client;

    /**
     * 登录时设备号
     */
    private String deviceId;

    /**
     * 登录ip
     */
    private String lastIp;

    /**
     * 手机系统
     */
    private String os;

    /**
     * 系统版本
     */
    private String osVersion;

    /**
     * 登录备注
     */
    private String text;

    /**
     * 登录时间
     */
    private Timestamp loginTime;


}

