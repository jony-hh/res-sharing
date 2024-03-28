package com.jony.security.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

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
