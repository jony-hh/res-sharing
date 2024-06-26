package com.jony.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * resVideoDetail表-实体类
 *
 * @author jony
 * @since 2024-03-12 12:19:15
 */
@TableName("res_video_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResVideoDetail implements Serializable {
    /**
     * 雪花漂移id
     */
    private Long id;

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

    /**
     * 发布时间
     */
    private Timestamp publishTime;

    /**
     * 发布状态 0草稿 1已发布
     */
    private Integer publishStatus;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 删除标识  0：正常   1：封禁   2：删除
     */
    private Integer deleted;

    /**
     * 创建者
     */
    private Long creator;

    /**
     * 创建时间
     */
    private Timestamp createdTime;

    /**
     * 更新者
     */
    private Long updater;

    /**
     * 修改时间
     */
    private Timestamp updatedTime;


}

