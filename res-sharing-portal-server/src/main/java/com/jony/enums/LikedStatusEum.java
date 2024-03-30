package com.jony.enums;

import lombok.Getter;

@Getter
public enum LikedStatusEum {
    LIKE(1, "点赞"),
    UNLIKE(0, "未点赞/取消点赞"),
    ERROR(-1, "操作异常");

    private final Integer code;
    private final String msg;

    LikedStatusEum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
