package com.jony.security.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author ：谁书-ss
 * @date ：2023-03-12 16:48
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：缓存登录 Token
 * <p></p>
 */
@Setter
@Getter
@ToString
public class AuthTokenCacheDto {

    private String authToken;

    private Timestamp createTime;

}
