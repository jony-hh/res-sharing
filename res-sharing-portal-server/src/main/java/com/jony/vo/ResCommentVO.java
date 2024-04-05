package com.jony.vo;

import com.jony.entity.SysUser;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ResCommentVO {
    /**
     * 评论id
     */
    private Long id;

    /**
     * 父评论id，根评论为-1
     */
    private Long parentId;

    /**
     * 目标id（隔离评论类型）
     */
    private Long contentId;

    /**
     * 发布评论的用户id
     */
    private Long uid;

    /**
     * 回复某个用户的id（根评论为-1）
     */
    private Long replyId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Timestamp createdTime;

    /**
     * 点赞数
     */
    Integer likes;

    /**
     * 评论的用户
     */
    SysUser user;

    /**
     * 回复的评论
     */
    List<ResCommentVO> reply;
}

