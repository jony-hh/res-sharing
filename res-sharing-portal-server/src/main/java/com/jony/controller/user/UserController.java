package com.jony.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jony.annotation.AuthCheck;
import com.jony.api.CommonResult;
import com.jony.api.ResultCode;
import com.jony.dto.UserLoginDTO;
import com.jony.dto.UserRegisterDTO;
import com.jony.dto.UserUpdateDTO;
import com.jony.entity.ResVideo;
import com.jony.entity.SysUser;
import com.jony.entity.UserQuestion;
import com.jony.mapper.ResVideoMapper;
import com.jony.mapper.SysUserMapper;
import com.jony.mapper.UserQuestionMapper;
import com.jony.service.UserService;
import com.jony.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/center")
@Tag(name = "用户中心")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SysUserMapper userMapper;
    private final UserQuestionMapper questionMapper;
    private final ResVideoMapper videoMapper;


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

    @PostMapping("localRegister")
    @Operation(summary = "注册站内账户")
    public CommonResult<?> localRegister(@RequestBody UserRegisterDTO userRegisterDTO) {
        boolean result = userService.localRegister(userRegisterDTO);
        if (result) {
            return CommonResult.success(ResultCode.SUCCESS);
        }
        return CommonResult.failed("登录失败");
    }

    @PutMapping("update")
    @Operation(summary = "用户更新信息")
    public CommonResult<?> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        boolean result = userService.updateUser(userUpdateDTO);
        if (result) {
            return CommonResult.success(ResultCode.SUCCESS);
        }
        return CommonResult.failed("更新失败");
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

    @GetMapping("logout")
    @Operation(summary = "退出登录")
    public CommonResult<?> logout(HttpServletRequest request) {
        boolean result = userService.logout(request);
        if (result) {
            return CommonResult.success(ResultCode.SUCCESS);
        }
        return CommonResult.failed("退出异常");
    }

    // endregion


    // region 【用户查询】

    @GetMapping("authorInfo")
    @Operation(summary = "作者信息")
    public CommonResult<?> authorInfo(@RequestParam("id") Long id) {
        // 不知道前端传来的id是什么数据的id，所以先查question表，再查user表
        UserQuestion question = questionMapper.selectById(id);
        if (question != null) {
            SysUser sysUser = userMapper.selectById(question.getUserId());
            if (sysUser != null) {
                return CommonResult.success(sysUser);
            }
        }

        ResVideo resVideo = videoMapper.selectById(id);
        if (resVideo != null) {
            SysUser sysUser = userMapper.selectById(resVideo.getUserId());
            if (sysUser != null) {
                return CommonResult.success(sysUser);
            }
        }
        return CommonResult.failed("查询失败");
    }

    @GetMapping("searchUser")
    @Operation(summary = "根据昵称模糊搜索用户")
    public CommonResult<?> searchUser(@RequestParam("keyword") String keyword) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(SysUser::getNickname, keyword);
        List<SysUser> userList = userService.list(queryWrapper);
        List<UserVO> userVOs = userList.stream()
                .map(user -> {
                    UserVO userVO = new UserVO();
                    userVO.setNickname(user.getNickname());
                    userVO.setAvatar(user.getAvatar());
                    userVO.setIntroduction(user.getIntroduction());
                    return userVO;
                })
                .collect(Collectors.toList());
        return CommonResult.success(userVOs);
    }

    // endregion

}
