package com.jony.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * resNews表-实体类
 *
 * @author jony
 * @since 2024-03-12 12:19:12
 */
@TableName("res_news")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResNews implements Serializable {
    /**
     * 雪花漂移id
     */
    private Long id;

    /**
     * 资讯标题
     */
    private String title;

    /**
     * 资讯所属标签
     */
    private String tag;

    /**
     * 作者
     */
    private String author;

    /**
     * 用于特殊操作：slideshow轮播图，grid栅格，default默认
     */
    private String flag;

    /**
     * 咨询内容
     */
    private String content;

    /**
     * 资讯摘要
     */
    private String summary;

    /**
     * banner地址
     */
    private String bannerUrl;

    /**
     * 文章地址
     */
    private String articleUrl;

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

