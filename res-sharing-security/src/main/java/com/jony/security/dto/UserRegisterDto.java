package com.jony.security.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wuZhenFeng
 * @date 2023/3/7 15:44
 */
@Setter
@Getter
@ToString
public class UserRegisterDto {
    private String username;

    private String password;

    private String confirmPassword;

    private String captcha;

}
