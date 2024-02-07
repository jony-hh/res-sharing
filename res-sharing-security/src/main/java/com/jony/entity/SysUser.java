package com.jony.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * sysUser表-实体类
 *
 * @author jony
 * @since 2024-02-07 14:32:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser implements Serializable {
    // 雪花漂移id
    private Long id;
    // 站内用户账号，必须唯一
    private String username;
    // 站内账号的密码
    private String password;
    // 用户昵称
    private String nickname;
    // 用户个人签名
    private String signature;
    // 用户性别 0-female 1-male
    private Long gender;
    // 用户生日
    private Long birthday;
    // 手机号码(唯一)
    private String mobile;
    // 手机号码绑定时间
    private Timestamp mobileBindTime;
    // 用户状态（0权限缺失，1权限正常）
    private Long status;
    // 邮箱(唯一)
    private String email;
    // 邮箱绑定时间
    private Timestamp emailBindTime;
    // 头像
    private String avatar;
    // 头像 200x200x80
    private String avatar200;
    // 原图头像
    private String avatarSrc;
    // 用户设备push_token
    private String pushToken;
    // 版本号
    private Integer version;
    // 删除标识  0：正常   1：封禁   2：删除
    private Integer deleted;
    // 创建者
    private Long creator;
    // 创建时间
    private Timestamp createdTime;
    // 更新者
    private Long updater;
    // 修改时间
    private Timestamp updatedTime;

}

