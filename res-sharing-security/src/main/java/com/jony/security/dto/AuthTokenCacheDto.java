package com.jony.security.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author jony
 * @date ：2023-03-12 16:48
 * @description ：缓存登录 Token
 */
@Setter
@Getter
@ToString
public class AuthTokenCacheDto {

    private String authToken;

    private Timestamp createTime;

}
