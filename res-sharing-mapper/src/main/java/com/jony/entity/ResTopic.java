package com.jony.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * resTopic表-实体类
 *
 * @author jony
 * @since 2024-03-12 12:19:14
 */
@TableName("res_topic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResTopic implements Serializable {
    /**
     * 雪花漂移id
     */
    private Long id;

    /**
     * 话题元素标题
     */
    private String title;

    /**
     * 用于特殊操作
     */
    private String flag;

    /**
     * 话题元素内容
     */
    private String content;

    /**
     * 话题元素摘要
     */
    private String summary;

    /**
     * 背景地址
     */
    private String backgroundUrl;

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

