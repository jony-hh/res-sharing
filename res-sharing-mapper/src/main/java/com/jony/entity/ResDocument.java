package com.jony.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * resDocument表-实体类
 *
 * @author jony
 * @since 2024-03-12 12:19:07
 */
@TableName("res_document")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResDocument implements Serializable {
    /**
     * 雪花漂移id
     */
    private Long id;

    /**
     * userId
     */
    private Long userId;

    /**
     * 文档标题
     */
    private String title;

    /**
     * 发布者
     */
    private String author;

    /**
     * 用于特殊操作
     */
    private String flag;

    /**
     * 文档类型icon地址（word、pdf、ppt...）
     */
    private String typeUrl;

    /**
     * 文档直链
     */
    private String documentUrl;

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

