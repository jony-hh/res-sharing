package com.jony.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * feat: 权限校验
 *
 * @author jony
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    /**
     * feat: 必须有某个角色
     *
     * @return String
     */
    String mustRole() default "";

}

