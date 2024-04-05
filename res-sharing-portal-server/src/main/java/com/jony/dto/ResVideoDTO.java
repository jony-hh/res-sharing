package com.jony.dto;

import lombok.Data;

@Data
public class ResVideoDTO {

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 讲师
     */
    private String lecturer;

    /**
     * 课程标识：国家示范
     */
    private String sign;

    /**
     * 封面地址
     */
    private String coverUrl;

    /**
     * 归属大学
     */
    private String college;

}
