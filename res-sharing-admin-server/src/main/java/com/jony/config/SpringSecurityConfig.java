package com.jony.config;


import com.jony.security.cache.ResourceService;
import com.jony.security.config.LoginPolicyConfig;
import com.jony.security.filter.LocalLoginFilter;
import com.jony.security.filter.LoginFilter;
import com.jony.security.filter.LoginPolicyFilter;
import com.jony.security.handler.GlobalAuthenticationHandler;
import com.jony.security.manager.DynamicAuthorizationManager;
import com.jony.security.utils.SpringSecurityUtils;
import com.jony.service.SysUserService;
import com.jony.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @author jony
 * @date ：2022-12-29 21:59
 * @description ：SpringSecurity 配置
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final GlobalAuthenticationHandler globalAuthenticationHandler;
    /**
     * 不同的登录类型，可以创建不同类型的过滤器分别处理，
     * 也可以只创建一个 Filter：LoginFilter，然后根据不同类型的登录url，来创建不同的 Token
     */
    private final TokenUtils tokenUtils;
    private final LoginPolicyConfig loginPolicyConfig;
    private final ResourceService resourceService;
    private final SysUserService userService;
    private final DynamicAuthorizationManager dynamicAuthorizationManager;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        String[] ignoreUrlArray = SpringSecurityUtils.ignoreUrlArray();
        httpSecurity
                // 配置请求授权规则
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers(ignoreUrlArray).permitAll()
                                .anyRequest().authenticated()
                )
                // 禁用默认表单登录
                .formLogin(AbstractHttpConfigurer::disable)

                // 添加自定义过滤器到UsernamePasswordAuthenticationFilter之前
                .addFilterBefore(new LoginPolicyFilter(loginPolicyConfig, tokenUtils, userService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new LoginFilter(tokenUtils, resourceService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new LocalLoginFilter(tokenUtils), UsernamePasswordAuthenticationFilter.class)

                // 配置退出登录
                .logout(logout -> logout.logoutUrl(SpringSecurityUtils.LOGOUT_URL).logoutSuccessHandler(globalAuthenticationHandler))

                // 禁用CSRF
                .csrf(AbstractHttpConfigurer::disable)

                // 配置异常处理
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler(globalAuthenticationHandler)
                                .authenticationEntryPoint(globalAuthenticationHandler))

                // 配置会话管理策略为无状态
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // 构建并返回安全过滤器链
        return httpSecurity.build();
    }

}
