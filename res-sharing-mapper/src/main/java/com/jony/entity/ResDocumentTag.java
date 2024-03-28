package com.jony.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * resDocumentTag表-实体类
 *
 * @author jony
 * @since 2024-03-12 12:19:10
 */
@TableName("res_document_tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResDocumentTag implements Serializable {
    /**
     * 雪花漂移id
     */
    private Long id;

    /**
     * 标签id
     */
    private Integer tagId;

    /**
     * 文档id
     */
    private Long documentId;

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

