package com.jony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResDocumentDTO {
    /**
     * 文档标题
     */
    private String title;

    /**
     * 发布者
     */
    private String author;

    /**
     * 文档直链
     */
    private String documentUrl;


}
