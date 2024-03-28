package com.jony.aop;


import com.jony.annotation.AuthCheck;
import com.jony.entity.SysUser;
import com.jony.enums.UserRoleEnum;
import com.jony.exception.ErrorCode;
import com.jony.exception.ServerException;
import com.jony.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;


/**
 * feat: 权限校验 AOP
 *
 * @author jony
 */
@Aspect
@Component
@RequiredArgsConstructor
public class AuthInterceptor {

    private final UserService userService;


    /**
     * feat: 执行拦截
     *
     * @param joinPoint
     * @param authCheck
     * @return
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole = authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        SysUser loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new ServerException(ErrorCode.NOT_LOGIN_ERROR);
        }

        // 必须有该权限才通过
        if (StringUtils.isNotBlank(mustRole)) {
            UserRoleEnum mustUserRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
            if (mustUserRoleEnum == null) {
                throw new ServerException(ErrorCode.NO_AUTH_ERROR);
            }
            List<String> userRoles = userService.getUserRole(loginUser.getId());
            // 如果被封号，直接拒绝
            if (UserRoleEnum.BAN.equals(mustUserRoleEnum)) {
                throw new ServerException(ErrorCode.NO_AUTH_ERROR);
            }
            // 必须有管理员权限
            if (UserRoleEnum.ADMIN.equals(mustUserRoleEnum)) {
                if (!userRoles.contains(mustRole)) {
                    throw new ServerException(ErrorCode.NO_AUTH_ERROR);
                }
            }
        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}

