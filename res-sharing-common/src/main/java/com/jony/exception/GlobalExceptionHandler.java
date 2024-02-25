package com.jony.exception;

import com.jony.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * feat: 全局异常处理器
 *
 * @author jony
 *
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ServerException.class)
    public CommonResult<?> businessExceptionHandler(ServerException e) {
        log.error("BusinessException", e);
        return CommonResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public CommonResult<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return CommonResult.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
