package com.jony.controller.user;

import com.jony.annotation.AuthCheck;
import com.jony.api.CommonResult;
import com.jony.dto.UserThumbDTO;
import com.jony.enums.LikedStatusEum;
import com.jony.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public CommonResult<?> thumb(HttpServletRequest request,@RequestBody UserThumbDTO userThumbDTO) {
        LikedStatusEum likedStatusEum = userService.thumb(request,userThumbDTO);
        return CommonResult.success(likedStatusEum.getCode(), likedStatusEum.getMsg());
    }

    // endregion

}
