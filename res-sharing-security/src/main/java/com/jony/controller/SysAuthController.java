package com.jony.controller;


import com.jony.api.CommonResult;
import com.jony.security.SpringSecurityUtils;
import com.jony.security.vo.UserInfoVo;
import com.jony.security.dto.UserLoginDto;
import com.jony.enums.UserEnum;
import com.jony.utils.TokenUtils;
import com.jony.service.SysAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * sysAuth表-控制器层
 *
 * @author jony
 * @since 2024-02-07 13:12:59
 */
@RestController
@RequestMapping("sysAuth")
@RequiredArgsConstructor
public class SysAuthController {

    private final SysAuthService sysAuthService;
    private final TokenUtils tokenUtils;

    @PostMapping("logout")
    public CommonResult<String> logout(HttpServletRequest request) {
        System.out.println("退出登录----------- logout");
        tokenUtils.logout(request);
        return CommonResult.success("退出登录", null);
    }

    @PostMapping("local")
    public CommonResult<UserInfoVo> local(@RequestBody UserLoginDto userLoginDTO, HttpServletResponse response) {
        System.out.println("登录----------- local");
        UserInfoVo userInfoVo = sysAuthService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword(), UserEnum.AuthType.LOCAL, userLoginDTO.getIsRememberMe(), response);
        return CommonResult.success(userInfoVo);
    }

    @PostMapping("email")
    public CommonResult<UserInfoVo> email(@RequestBody UserLoginDto userLoginDTO, HttpServletResponse response) {
        System.out.println("登录----------- email");
        return CommonResult.success(sysAuthService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword(), UserEnum.AuthType.EMAIL, userLoginDTO.getIsRememberMe(), response));
    }

    @PostMapping("phone")
    public CommonResult<UserInfoVo> phone(@RequestBody UserLoginDto userLoginDTO, HttpServletResponse response) {
        System.out.println("登录----------- phone");
        SpringSecurityUtils.login(userLoginDTO.getUsername(), userLoginDTO.getPassword(), UserEnum.AuthType.LOCAL);
        return CommonResult.success(sysAuthService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword(), UserEnum.AuthType.PHONE, userLoginDTO.getIsRememberMe(), response));
    }
}

