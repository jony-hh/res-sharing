package com.jony.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {

    String username;

    String password;

    String confirmPassword;

    String authType;

}
