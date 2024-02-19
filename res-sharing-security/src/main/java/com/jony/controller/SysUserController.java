package com.jony.controller;


import com.jony.api.CommonResult;
import com.jony.service.SysUserService;
import com.jony.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * sysUser表-控制层
 *
 * @author jony
 * @since 2024-02-07 14:32:03
 */
@RestController
@RequestMapping("sys/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;
    private final TokenUtils tokenUtils;


    @PostMapping("logout")
    public CommonResult<String> logout(HttpServletRequest request) {
        System.out.println("退出登录----------- logout");
        tokenUtils.logout(request);
        return CommonResult.success("退出登录成功", null);
    }
}

