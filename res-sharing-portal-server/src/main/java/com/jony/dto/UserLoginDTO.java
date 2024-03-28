package com.jony.dto;

import lombok.Data;

@Data
public class UserLoginDTO {

    String username;

    String password;

    String authType;

    Boolean isRememberMe;

}
