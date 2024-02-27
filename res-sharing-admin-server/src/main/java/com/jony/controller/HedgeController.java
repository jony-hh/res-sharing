package com.jony.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/index")
    @Operation(summary = "启动测试")
    @PreAuthorize("hasAuthority('sys:user:index')")
    public String index() {
        return "您好，项目已启动，祝您使用愉快！";
    }

    @PostMapping("/modify")
    @Operation(summary = "修改系统用户密码")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public String modify(String pwd,Integer id){
        return "修改成功！";
    }
}
