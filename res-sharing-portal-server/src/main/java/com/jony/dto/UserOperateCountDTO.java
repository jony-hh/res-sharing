package com.jony.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserOperateCountDTO implements Serializable {

    private Long contentId;
    private Integer value;

    public UserOperateCountDTO(Long contentId, Integer value) {
        this.contentId = contentId;
        this.value = value;
    }
}