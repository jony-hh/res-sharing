package com.jony.dto;

import lombok.Data;

@Data
public class ResCommentDTO {

    /**
     * 发布评论的用户id
     */
    private Long uid;

    /**
     * 目标id（隔离评论类型）
     */
    private Long contentId;

    /**
     * 父评论id，根评论为-1
     */
    private Long parentId;

    /**
     * 回复某个用户的id（根评论为-1）
     */
    private Long replyId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论类型（0代表课程course评论，1代表话题topic评论...）
     */
    private String type;
}
