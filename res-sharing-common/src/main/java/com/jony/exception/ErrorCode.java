package com.jony.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误编码
 *
 * @author jony
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    SUCCESS(200, "ok"),
    PARAMS_ERROR(400, "请求参数错误"),
    REFRESH_TOKEN_INVALID(400, "refresh_token 已失效"),
    NOT_LOGIN_ERROR(401, "未登录"),
    UNAUTHORIZED(401, "还未授权，不能访问"),
    NO_AUTH_ERROR(402, "无权限"),
    FORBIDDEN(403, "没有权限，禁止访问"),
    NOT_FOUND_ERROR(404, "请求数据不存在"),
    FILE_TYPE_ERROR(405, "文件类型错误"),
    INTERNAL_SERVER_ERROR(500, "服务器异常，请稍后再试"),
    OPERATION_ERROR(501, "操作失败"),
    UPLOAD_FAILED(500, "上传失败");


    private final int code;
    private final String msg;
}
