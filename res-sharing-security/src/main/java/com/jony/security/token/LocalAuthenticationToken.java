package com.jony.security.token;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.util.Collection;

/**
 * @author jony
 * @date ：2023-03-05 17:42
 * @description ：本地登录 token
 */
public class LocalAuthenticationToken extends UsernamePasswordAuthenticationToken {
    @Serial
    private static final long serialVersionUID = 8634771078835164272L;

    public LocalAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public LocalAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}
