package com.jony.controller;

import com.jony.api.CommonResult;
import com.jony.enums.UserEnum;
import com.jony.security.dto.UserRegisterDto;
import com.jony.security.vo.UserInfoVo;
import com.jony.service.SysAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys/register")
@RequiredArgsConstructor
@Tag(name = "注册账户")
public class SysRegisterController {

    private final SysAuthService sysAuthService;

    @PostMapping("local")
    @Operation(summary = "注册站内账户")
    public CommonResult<UserInfoVo> localRegister(@RequestBody UserRegisterDto userRegisterDto, HttpServletResponse response) {
        boolean result = sysAuthService.register(userRegisterDto, UserEnum.AuthType.LOCAL.getType(), response);
        if (result) {
            return CommonResult.success(null, "注册成功");
        }
        return CommonResult.failed("注册失败");
    }

}
