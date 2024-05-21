package com.jony.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {

    private Long id;

    // 用户昵称
    private String nickname;

    // 新加字段
    private String profession;

    private String company;

    private String interests;

    private String introduction;

}
