package com.jony.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jony.exception.ApiResponse;
import com.jony.exception.BusinessException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author jony
 * @date ：2023-02-28 21:16
 * @description ：Response 工具类
 */
@Component
public class ResponseUtils {
    private static ObjectMapper objectMapper;

    public ResponseUtils(ObjectMapper objectMapper) {
        ResponseUtils.objectMapper = objectMapper;
    }


    /**
     * 响应字符串
     *
     * @param response    响应对象
     * @param apiResponse 响应对象
     */
    public static void responseJson(HttpServletResponse response, ApiResponse<?> apiResponse) {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        } catch (IOException e) {
            throw new BusinessException("序列化异常：" + e.getMessage());
        }
    }

}
