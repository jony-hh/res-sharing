package com.jony.exception;


import com.jony.api.CommonResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jony
 * @date ：2022-12-25 11:43
 * @description ：全局异常
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    ApiResponse<String> handleException(Exception e) {
        log.info("Exception：", e);
        return ApiResponse.of(ApiResponse.Type.ERROR.value(), e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    ApiResponse<String> handleBusinessException(BusinessException e) {
        log.warn("BusinessException：{}", e.getMessage());
        return ApiResponse.of(e.getCode(), e.getLocalizedMessage());
    }

    @ExceptionHandler(ServerException.class)
    public CommonResult<?> businessExceptionHandler(ServerException e) {
        log.warn("ServerException：{}", e.getMessage());
        return CommonResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    ApiResponse<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.warn("缺少参数：{}", e.getMessage());
        return ApiResponse.of(ApiResponse.Type.ERROR.value(), e.getParameterName());
    }

    @ExceptionHandler(AccessDeniedException.class)
    ApiResponse<String> handleAccessDeniedException(AccessDeniedException e) {
        log.warn("无权限访问：{}", "此用户没有这个权限哦");
        return ApiResponse.of(ApiResponse.Type.FORBIDDEN.value(),e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ApiResponse<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        printParameterMap();
        return ApiResponse.of(ApiResponse.Type.ERROR.value(), errorMessage(e.getBindingResult()));
    }

    @ExceptionHandler(BindException.class)
    ApiResponse<String> handleBindException(BindException e) {
        printParameterMap();
        return ApiResponse.of(ApiResponse.Type.ERROR.value(), errorMessage(e.getBindingResult()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    ApiResponse<String> handleBindException(UsernameNotFoundException e) {
        printParameterMap();
        return ApiResponse.of(ApiResponse.Type.UN_AUTH.value(), e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    ApiResponse<String> handleBindException(BadCredentialsException e) {
        printParameterMap();
        return ApiResponse.of(ApiResponse.Type.UN_AUTH.value(), e.getMessage());
    }

    // region 工具方法

    private void printParameterMap() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Map<String, String[]> paramMap = request.getParameterMap();
            Map<String, String> newParamMap = convertRequestParamMap(paramMap);
            log.info(String.format("请求地址：%s", request.getRequestURL()));
            log.info(String.format("请求参数：%s", newParamMap));
        }
    }

    public static String errorMessage(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            for (FieldError error : list) {
                errorMessage.append(",").append(error.getField()).append(" : ").append(error.getDefaultMessage());
            }
        }
        return errorMessage.toString().replaceFirst(",", "");
    }

    public static Map<String, String> convertRequestParamMap(Map<String, String[]> paramMap) {
        Map<String, String> newParamMap = new HashMap<>(10);
        if (paramMap != null) {
            for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
                String key = entry.getKey();
                String[] values = entry.getValue();
                newParamMap.put(key, values[0]);
            }
        }
        return newParamMap;
    }

    // endregion
}
