package com.jony.security.config;


import com.jony.security.filter.*;
import com.jony.security.handler.GlobalAuthenticationHandler;
import com.jony.security.utils.SpringSecurityUtils;
import com.jony.security.manager.DynamicAuthorizationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final GlobalAuthenticationHandler globalAuthenticationHandler;
    /**
     * 不同的登录类型，可以创建不同类型的过滤器分别处理，
     * 也可以只创建一个 Filter：LoginFilter，然后根据不同类型的登录url，来创建不同的 Token
     */
    private final LocalLoginFilter localLoginFilter;
    private final EmailLoginFilter emailLoginFilter;
    private final PhoneLoginFilter phoneLoginFilter;
    private final LoginFilter loginFilter;
    private final LoginPolicyFilter loginPolicyFilter;
    private final DynamicAuthorizationManager dynamicAuthorizationManager;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // 路径配置 （authorizeRequests 方法已废弃，取而代之的是 authorizeHttpRequests）
        // http.antMatcher()不再可用，并被替换为 http.securityMatcher() 或 httpSecurity.requestMatchers()
        // SpringSecurityUtils.ignoreUrlArray() 可以只配置注册登录相关页面，其它所有权限放到数据库，
        // 通过 dynamicAuthorizationManager 动态权限决策管理器，来动态管理
        // 6.0.1 已废弃
        // httpSecurity.authorizeHttpRequests()
        //         .requestMatchers(SpringSecurityUtils.ignoreUrlArray()).permitAll()
        //         .anyRequest()
        //         .access(dynamicAuthorizationManager);

        httpSecurity.authorizeHttpRequests(authorizeHttpRequests ->
                authorizeHttpRequests
                        .requestMatchers(SpringSecurityUtils.ignoreUrlArray()).permitAll()
                        .anyRequest().authenticated()
        );

        httpSecurity
                .formLogin(formLogin -> formLogin.disable())
                .addFilterBefore(loginPolicyFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(localLoginFilter, UsernamePasswordAuthenticationFilter.class)
                //.addFilterBefore(emailLoginFilter, UsernamePasswordAuthenticationFilter.class)
                //.addFilterBefore(phoneLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout.logoutUrl(SpringSecurityUtils.LOGOUT_URL).logoutSuccessHandler(globalAuthenticationHandler));

        // httpSecurity.rememberMe().rememberMeServices(dbRememberMeServices)
        httpSecurity
                .csrf(csrf -> csrf.disable())
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                //.and()
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(globalAuthenticationHandler).authenticationEntryPoint(globalAuthenticationHandler));

        httpSecurity
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();
    }

}