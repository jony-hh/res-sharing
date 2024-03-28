package com.jony.controller;

import com.jony.api.CommonResult;
import com.jony.api.ResultCode;
import com.jony.entity.SysUser;
import com.jony.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页 欢迎信息
 *
 * @author jony
 */
@RestController
@RequestMapping("/hedge")
@AllArgsConstructor
@Tag(name = "紧急避险")
public class HedgeController {

    private final PasswordEncoder passwordEncoder;
    private final SysUserService sysUserService;


    @GetMapping("/index")
    @Operation(summary = "启动测试")
    // @PreAuthorize("hasAuthority('sys:user:index')")
    public CommonResult<?> index() {
        return CommonResult.success(ResultCode.START_SUCCESS);
    }

    @PostMapping("/modify")
    @Operation(summary = "修改系统用户密码")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public CommonResult<?> modify(String pwd, Integer id) {
        String encodePwd = passwordEncoder.encode(pwd);
        SysUser user = sysUserService.getById(id);
        user.setPassword(encodePwd);
        sysUserService.updateById(user);
        return CommonResult.success(ResultCode.SUCCESS);
    }
}
