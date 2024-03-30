package com.jony.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserThumbCountDTO implements Serializable {

    private Long contentId;
    private Integer value;

    public UserThumbCountDTO(Long contentId, Integer value) {
        this.contentId = contentId;
        this.value = value;
    }
}