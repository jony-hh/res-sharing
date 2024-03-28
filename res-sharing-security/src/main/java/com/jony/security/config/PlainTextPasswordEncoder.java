package com.jony.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;


@Component
@RequiredArgsConstructor
public class PlainTextPasswordEncoder implements PasswordEncoder {

    /**
     * feat: 盐值，混淆密码
     */
    @Value("${shuishu.encrypt.salt}")
    private String salt;

    @Override
    public String encode(CharSequence rawPassword) {
        return md5DigestAsHex(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String hashedPassword = encode(rawPassword);
        return encodedPassword.equals(hashedPassword);
    }

    private String md5DigestAsHex(String input) {
        return DigestUtils.md5DigestAsHex((salt + input).getBytes());
    }
}
