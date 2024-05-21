package com.jony.controller;


import com.jony.api.CommonResult;
import com.jony.api.ResultCode;
import com.jony.entity.SysUser;
import com.jony.security.dto.EditUserDTO;
import com.jony.security.vo.UserInfoVo;
import com.jony.service.SysUserService;
import com.jony.utils.TokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * sysUser表-控制层
 *
 * @author jony
 * @since 2024-02-07 14:32:03
 */
@RestController
@RequestMapping("sys/user")
@RequiredArgsConstructor
@Tag(name = "用户相关")
public class SysUserController {

    private final SysUserService sysUserService;
    private final TokenUtils tokenUtils;


    @GetMapping("loginUser")
    @Operation(summary = "获取登录用户信息")
    @PreAuthorize("hasAuthority('sys:user:page')")
    public CommonResult<UserInfoVo> getLoginUser(HttpServletRequest request) {
        UserInfoVo userInfoVo = tokenUtils.getUserInfoVo(request);
        return CommonResult.success(userInfoVo);
    }

    @PostMapping("logout")
    @Operation(summary = "退出登录")
    public CommonResult<String> logout(HttpServletRequest request) {
        tokenUtils.logout(request);
        return CommonResult.success(null, ResultCode.LOGOUT_SUCCESS.getMessage());
    }

    // region crud

    @GetMapping("list")
    @Operation(summary = "获取用户列表")
    public CommonResult<List<HashMap<String, Object>>> getUserList(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "pagenum", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer size) {
        List<HashMap<String, Object>> list = sysUserService.getList(query, page, size);
        return CommonResult.success(list);
    }

    @GetMapping("{id}")
    @Operation(summary = "获取某个用户")
    public CommonResult<SysUser> getUserById(@PathVariable("id") Long id) {
        SysUser user = sysUserService.getById(id);
        return CommonResult.success(user);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除用户")
    public CommonResult<?> deleteUser(@PathVariable("id") Long id) {
        boolean b = sysUserService.removeById(id);
        if (b) {
            return CommonResult.success(null, ResultCode.SUCCESS.getMessage());
        } else {
            return CommonResult.failed(ResultCode.OPERATION_FAILED.getMessage());
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "更改用户")
    public CommonResult<?> updateUser(@PathVariable("id") Long id, @RequestBody EditUserDTO editUser) {
        SysUser user = sysUserService.getById(id);
        user.setNickname(editUser.getNickname());
        user.setEmail(editUser.getEmail());
        user.setMobile(editUser.getMobile());
        boolean b = sysUserService.updateById(user);
        if (b) {
            return CommonResult.success(null, ResultCode.SUCCESS.getMessage());
        } else {
            return CommonResult.failed(ResultCode.OPERATION_FAILED.getMessage());
        }
    }

    @PutMapping("{id}/state/{status}")
    @Operation(summary = "更改用户")
    public CommonResult<?> updateUserStatus(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        SysUser user = sysUserService.getById(id);
        user.setStatus(status == 1 ? 0 : 1);
        boolean b = sysUserService.updateById(user);
        if (b) {
            return CommonResult.success(null, ResultCode.SUCCESS.getMessage());
        } else {
            return CommonResult.failed(ResultCode.OPERATION_FAILED.getMessage());
        }
    }


    // endregion
}

