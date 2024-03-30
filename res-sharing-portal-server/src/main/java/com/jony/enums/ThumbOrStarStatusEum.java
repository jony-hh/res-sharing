package com.jony.enums;

import lombok.Getter;

@Getter
public enum ThumbOrStarStatusEum {
    THUMB(1, "点赞"),
    UN_THUMB(0, "未点赞/取消点赞"),
    STAR(1, "收藏"),
    UN_STAR(0, "未收藏/取消收藏"),
    ERROR(-1, "操作异常");

    private final Integer code;
    private final String msg;

    ThumbOrStarStatusEum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
