package com.jony.controller;


import com.jony.api.CommonResult;
import com.jony.enums.UserEnum;
import com.jony.security.dto.UserLoginDto;
import com.jony.service.SysAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * sysAuth表-控制器层
 *
 * @author jony
 * @since 2024-02-07 13:12:59
 */
@RestController
@RequestMapping("sys/auth")
@RequiredArgsConstructor
@Tag(name = "登录认证")
public class SysAuthController {

    private final SysAuthService sysAuthService;

    @PostMapping("local")
    @Operation(summary = "站内账户登录")
    public CommonResult<HashMap<String, String>> local(@RequestBody UserLoginDto userLoginDTO, HttpServletResponse response) {
        System.out.println("登录----------- local");
        String token = sysAuthService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword(), UserEnum.AuthType.LOCAL, userLoginDTO.getIsRememberMe(), response);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        return CommonResult.success(map);
    }

    // @PostMapping("email")
    // @Operation(summary = "邮箱登录")
    // public CommonResult<UserInfoVo> email(@RequestBody UserLoginDto userLoginDTO, HttpServletResponse response) {
    //     System.out.println("登录----------- email");
    //     return CommonResult.success(sysAuthService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword(), UserEnum.AuthType.EMAIL, userLoginDTO.getIsRememberMe(), response));
    // }
    //
    // @PostMapping("phone")
    // @Operation(summary = "手机登录")
    // public CommonResult<UserInfoVo> phone(@RequestBody UserLoginDto userLoginDTO, HttpServletResponse response) {
    //     System.out.println("登录----------- phone");
    //     SpringSecurityUtils.login(userLoginDTO.getUsername(), userLoginDTO.getPassword(), UserEnum.AuthType.LOCAL);
    //     return CommonResult.success(sysAuthService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword(), UserEnum.AuthType.PHONE, userLoginDTO.getIsRememberMe(), response));
    // }
}

