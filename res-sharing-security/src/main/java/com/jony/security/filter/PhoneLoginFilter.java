package com.jony.security.filter;


import com.jony.exception.ApiResponse;
import com.jony.security.token.PhoneAuthenticationToken;
import com.jony.security.utils.SpringSecurityUtils;
import com.jony.security.vo.UserInfoVo;
import com.jony.utils.ResponseUtils;
import com.jony.utils.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * @author jony
 * @date ：2023-03-05 14:22
 * @description ：手机号登录过滤器
 * 合并的方式：所有类型方式的登录，登录过滤器逻辑统一在 {@link LoginFilter}
 */
@Slf4j
@RequiredArgsConstructor
public class PhoneLoginFilter extends OncePerRequestFilter {

    private final TokenUtils tokenUtils;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.info("【PhoneLoginFilter 过滤器】执行doFilterInternal()方法");
        // 获取URI
        String requestUri = request.getRequestURI();
        if (SpringSecurityUtils.LOGIN_URL_LOCAL.contains(requestUri) && !HttpMethod.POST.matches(request.getMethod())) {
            ResponseUtils.responseJson(response, new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "不支持的请求方式"));
            return;
        }
        // 获取用户信息
        UserInfoVo userInfoVo = tokenUtils.getUserInfoVo(request, response);
        // 忽略URL
        if (SpringSecurityUtils.existsInIgnoreUrlArray(requestUri)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 需要认证的URL，token过期无效
        if (userInfoVo == null) {
            ResponseUtils.responseJson(response, new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "请先登录，再访问资源"));
            return;
        }
        // 用户信息放到上下文
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            PhoneAuthenticationToken phoneAuthenticationToken = new PhoneAuthenticationToken(userInfoVo.getUserAuthIdentifier(), userInfoVo.getAuthorities());
            phoneAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(phoneAuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }


}
