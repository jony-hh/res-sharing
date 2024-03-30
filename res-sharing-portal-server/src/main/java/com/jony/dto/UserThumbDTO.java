package com.jony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserThumbDTO {

    private Long userId;

    private Long contentId;

    private String contentType;

    private Integer status;

}
