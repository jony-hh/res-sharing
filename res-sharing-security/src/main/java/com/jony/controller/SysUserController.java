package com.jony.controller;


import com.jony.api.CommonResult;
import com.jony.api.ResultCode;
import com.jony.security.vo.UserInfoVo;
import com.jony.service.SysUserService;
import com.jony.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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


    @GetMapping("loginUser")
    public CommonResult<UserInfoVo> getLoginUser(HttpServletRequest request) {
        UserInfoVo userInfoVo = tokenUtils.getUserInfoVo(request);
        return CommonResult.success(userInfoVo);
    }

    @PostMapping("logout")
    public CommonResult<String> logout(HttpServletRequest request) {
        tokenUtils.logout(request);
        return CommonResult.success(null, ResultCode.LOGOUT_SUCCESS.getMessage());
    }
}

