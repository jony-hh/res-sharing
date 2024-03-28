package com.jony.api;

import lombok.Getter;

/**
 * API返回码封装类
 * Created by macro on 2019/4/19.
 */
@Getter
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "success!"),
    START_SUCCESS(200, "您好，项目已启动，祝您使用愉快！"),
    LOGOUT_SUCCESS(200, "退出登录成功"),
    PARAMS_ERROR(400, "请求参数错误"),
    NOT_LOGIN_ERROR(401, "未登录"),
    NO_AUTH_ERROR(402, "无权限"),
    FORBIDDEN_ERROR(403, "禁止访问"),
    NOT_FOUND_ERROR(404, "请求数据不存在"),
    FILE_TYPE_ERROR(405, "文件类型错误"),
    VALIDATE_FAILED(406, "参数验证失败"),
    SYSTEM_ERROR(500, "系统内部异常"),
    OPERATION_FAILED(501, "操作失败");

    private final int code;
    private final String message;

    private ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
