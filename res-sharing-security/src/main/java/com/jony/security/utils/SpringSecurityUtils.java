package com.jony.security.utils;

import com.jony.enums.UserEnum;
import com.jony.exception.BusinessException;
import com.jony.security.config.PermitResource;
import com.jony.security.token.EmailAuthenticationToken;
import com.jony.security.token.LocalAuthenticationToken;
import com.jony.security.token.PhoneAuthenticationToken;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

/**
 * @author jony
 * @date ：2022-12-31 13:40
 * @description ：Spring Security 工具类
 */
public class SpringSecurityUtils {
    public static final String LOGOUT_URL = "/api/sys/user/logout";
    public static final String LOGIN_URL_LOCAL = "/api/sys/auth/local";
    public static final String LOGIN_URL_EMAIL = "/api/sys/auth/email";
    public static final String LOGIN_URL_PHONE = "/api/sys/auth/phone";
    public static final String PORTAL_ADMIN_URL = "/portal/api";
    public static final String LOGIN_USERNAME_KEY = "userAuthIdentifier";
    public static final String LOGIN_USERNAME_FRONT_KEY = "username";
    public static final String LOGIN_PASSWORD_KEY = "userAuthCredential";
    public static final String LOGIN_PASSWORD_FRONT_KEY = "password";
    public static final String LOGIN_CAPTCHA = "captcha";


    public static String getAuthType(String uri) {
        if (StringUtils.hasText(uri)) {
            if (uri.contains(LOGIN_URL_LOCAL)) {
                return UserEnum.AuthType.LOCAL.getType();
            }else if (uri.contains(LOGIN_URL_EMAIL)){
                return UserEnum.AuthType.EMAIL.getType();
            } else if (uri.contains(LOGIN_URL_PHONE)) {
                return UserEnum.AuthType.PHONE.getType();
            }
        }
        return null;
    }

    public static String[] ignoreUrlArray() {
        //yml配置文件有访问前缀context-path  SpringSecurity 这里就不能加前缀
        List<String> permitList = PermitResource.getPermitList();
        return permitList.toArray(new String[0]);
    }

    public static String[] authenticateUrlArray() {
        //yml配置文件有访问前缀context-path  SpringSecurity 这里就不能加前缀
        List<String> permitList = PermitResource.getNotPermitList();
        return permitList.toArray(new String[0]);
    }

    public static boolean existsInIgnoreUrlArray(String requestUri) {
        for (String ignoreUrl : ignoreUrlArray()) {
            if (requestUri.contains(ignoreUrl.replace("/**", ""))) {
                return true;
            }
        }
        return false;
    }

    public static boolean existsInAuthenticateUrlArray(String requestUri) {
        for (String authenticateUrl : authenticateUrlArray()) {
            if (requestUri.contains(authenticateUrl.replace("/**", ""))) {
                return true;
            }
        }
        return false;
    }

    public static boolean existsInIgnoreUrlArrayForDb(String requestUri, List<String> isNotAuthorizationUrlList) {
        if (!ObjectUtils.isEmpty(isNotAuthorizationUrlList)) {
            for (String dbIgnoreUrl : isNotAuthorizationUrlList) {
                if (requestUri.contains(dbIgnoreUrl.replace("/**", ""))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     */
    public static void login(String username, String password, UserEnum.AuthType authType) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        Authentication authentication = null;
        if (UserEnum.AuthType.LOCAL.equals(authType)) {
            SecurityContextHolder.getContext().setAuthentication(new LocalAuthenticationToken(username, password));
        } else if (UserEnum.AuthType.EMAIL.equals(authType)) {
            SecurityContextHolder.getContext().setAuthentication(new EmailAuthenticationToken(username, password));
        } else if (UserEnum.AuthType.PHONE.equals(authType)) {
            SecurityContextHolder.getContext().setAuthentication(new PhoneAuthenticationToken(username, password));
        } else {
            throw new BusinessException("不支持的登录方式");
        }
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }

}
