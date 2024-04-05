package com.jony.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {

    private Long id;

    // 站内用户账号，必须唯一
    private String username;
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
    // 邮箱(唯一)
    private String email;
    // 头像
    private String avatar;


    // 新加字段
    private String profession;

    private String company;

    private String interests;

    private String introduction;

}
