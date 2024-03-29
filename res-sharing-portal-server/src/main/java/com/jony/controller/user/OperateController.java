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
@RequestMapping("/user/operate")
@Tag(name = "用户操作")
@RequiredArgsConstructor
public class OperateController {

    private final UserService userService;


    // region 【用户点赞、收藏、浏览记录、动态】

    @PostMapping("thumb")
    @AuthCheck(mustRole = "user")
    @Operation(summary = "点赞操作")
    public CommonResult<?> thumb(HttpServletRequest request) {
        boolean result = userService.logout(request);
        if (result) {
            return CommonResult.success(ResultCode.SUCCESS);
        }
        return CommonResult.failed("退出异常");
    }

    // endregion

}
