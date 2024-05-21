package com.jony.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * userQuestion表-实体类
 *
 * @author jony
 * @since 2024-03-14 17:58:31
 */
@TableName("user_question")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuestion implements Serializable {
    /**
     * 问题id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 问题标题
     */
    private String title;

    /**
     * 问题内容
     */
    private String content;

    /**
     * 浏览量
     */
    private Integer pageview;

    /**
     * 回答数
     */
    private Integer answerCount;

    /**
     * 特殊标记
     */
    private String flag;

    /**
     * 是否被解决 0未解决 1已解决
     */
    private Integer resolveStatus;

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

