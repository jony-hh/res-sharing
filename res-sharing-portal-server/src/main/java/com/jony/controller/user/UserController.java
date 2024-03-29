package com.jony.controller.user;

import com.jony.annotation.AuthCheck;
import com.jony.api.CommonResult;
import com.jony.api.ResultCode;
import com.jony.dto.UserLoginDTO;
import com.jony.entity.SysUser;
import com.jony.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/user/center")
@Tag(name = "用户中心")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // region 【用户基本操作】

    @PostMapping("localLogin")
    @Operation(summary = "站内账户登录")
    public CommonResult<?> localLogin(@RequestBody UserLoginDTO userLoginDTO, HttpServletResponse response) {
        boolean result = userService.authenticate(userLoginDTO, response);
        if (result) {
            return CommonResult.success(ResultCode.SUCCESS);
        }
        return CommonResult.failed("登录失败");
    }

    @GetMapping("getLoginUser")
    @AuthCheck(mustRole = "user")
    @Operation(summary = "得到登录用户")
    public CommonResult<?> getLoginUser(HttpServletRequest request){
        SysUser loginUser = userService.getLoginUser(request);
        HashMap<Object, Object> loginUserMap = new HashMap<>();
        loginUserMap.put("loginUser", loginUser);
        return CommonResult.success(loginUserMap);
    }

    @PostMapping("logout")
    @Operation(summary = "退出登录")
    public CommonResult<?> logout(HttpServletRequest request) {
        boolean result = userService.logout(request);
        if (result) {
            return CommonResult.success(ResultCode.SUCCESS);
        }
        return CommonResult.failed("退出异常");
    }

    // endregion


    // region 【用户点赞、收藏、浏览记录、动态】

    @PostMapping("thumb")
    @Operation(summary = "用户点赞操作")
    public CommonResult<?> thumb(HttpServletRequest request) {
        boolean result = userService.logout(request);
        if (result) {
            return CommonResult.success(ResultCode.SUCCESS);
        }
        return CommonResult.failed("退出异常");
    }

    // endregion

}
