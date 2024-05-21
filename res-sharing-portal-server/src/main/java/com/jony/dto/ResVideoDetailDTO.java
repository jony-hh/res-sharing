package com.jony.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResVideoDetailDTO {

    /**
     * userId
     */
    private Long userId;

    /**
     * 所属课程id
     */
    private Long videoId;

    /**
     * 所属章节
     */
    private String chapter;

    /**
     * 视频地址
     */
    private String url;

    /**
     * 章节等级 0顶级 1下一级 2下两级
     */
    private Integer level;


}
