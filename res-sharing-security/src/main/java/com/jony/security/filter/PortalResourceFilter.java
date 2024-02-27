package com.jony.security.filter;

import com.jony.security.utils.SpringSecurityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @description 【前台系统】加上这个过滤器
 * 符合条件则返回，不走其它过滤器了
 */
@Slf4j
@RequiredArgsConstructor
public class PortalResourceFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // 获取需要认证的url
        String[] authenticateUrlArray = SpringSecurityUtils.authenticateUrlArray();
        // 获取URI
        String requestUri = request.getRequestURI();
        if (!ArrayUtils.contains(authenticateUrlArray, requestUri)) {
            // 直接返回，不走过滤器了
            return;
        }
        filterChain.doFilter(request, response);
    }
}
