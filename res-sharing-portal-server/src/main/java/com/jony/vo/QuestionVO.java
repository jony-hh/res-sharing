package com.jony.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionVO {
    /**
     * 问题id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 问题标题
     */
    private String title;

    /**
     * 浏览量
     */
    private Integer pageview;

    /**
     * 回答数
     */
    private Integer answerCount;

    /**
     * 是否被解决 0未解决 1已解决
     */
    private Integer resolveStatus;


    /**
     * 创建时间
     */
    private Timestamp createdTime;

    List<String> tags;
}
