package com.jony.controller;

import com.jony.api.CommonResult;
import com.jony.api.ResultCode;
import com.jony.dto.UserLoginDTO;
import com.jony.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/center")
@Tag(name = "用户中心")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("localLogin")
    @Operation(summary = "站内账户登录")
    public CommonResult<?> localLogin(@RequestBody UserLoginDTO userLoginDTO, HttpServletResponse response) {
        // 查库
        boolean result = userService.authenticate(userLoginDTO, response);
        if (result) {
            return CommonResult.success(ResultCode.SUCCESS);
        }
        return CommonResult.failed("登录失败");
    }
}
