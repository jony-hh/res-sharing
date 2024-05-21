package com.jony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResTopicDTO {

    /**
     * 话题元素标题
     */
    private String title;


    /**
     * 话题元素内容
     */
    private String content;


    /**
     * 背景地址
     */
    private String backgroundUrl;
}
