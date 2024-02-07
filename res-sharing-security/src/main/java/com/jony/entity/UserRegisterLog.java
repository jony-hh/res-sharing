package com.jony.entity;

import java.sql.Timestamp;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;


/**
 * userRegisterLog表-实体类
 *
 * @author jony
 * @since 2024-02-07 14:28:52
 */
@TableName("user_register_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterLog implements Serializable {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 注册方式1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博
     */
    private Long registerMethod;

    /**
     * 注册IP
     */
    private String registerIp;

    /**
     * 注册客户端
     */
    private String registerClient;

    /**
     * 注册时间
     */
    private Timestamp registerTime;


}

