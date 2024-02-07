package com.jony.controller;


import com.jony.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * sysUser表-控制层
 *
 * @author jony
 * @since 2024-02-07 14:32:03
 */
@RestController
@RequestMapping("sysUser")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;
}

