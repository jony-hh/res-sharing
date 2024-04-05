package com.jony.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * resComment表-实体类
 *
 * @author jony
 * @since 2024-04-05 15:39:07
 */
@TableName("res_comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResComment implements Serializable {
    /**
     * 评论id
     */     
    private Long id;
    
    /**
     * 父评论id，根评论为-1
     */     
    private Long parentId;
    
    /**
     * 评论类型（0代表课程course评论，1代表话题topic评论...）
     */     
    private String type;
    
    /**
     * 目标id（隔离评论类型）
     */     
    private Long contentId;
    
    /**
     * 发布评论的用户id
     */     
    private Long userId;
    
    /**
     * 回复某个用户的id（根评论为-1）
     */     
    private Long replyId;
    
    /**
     * 评论内容
     */     
    private String content;
    
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

