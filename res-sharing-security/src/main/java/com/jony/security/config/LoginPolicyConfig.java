package com.jony.security.config;


import com.jony.enums.UserEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author jony
 * @date ：2023-03-12 12:49
 * @description ：登录策略
 */
@Component
public class LoginPolicyConfig {

    @Value("${shuishu.login-policy}")
    private String loginPolicy;


    public String getLoginPolicy() {
        if (UserEnum.LoginPolicy.existLoginPolicy(loginPolicy)) {
            return loginPolicy;
        }
        // 返回默认的登录策略
        return UserEnum.LoginPolicy.ONE.name();
    }

}
