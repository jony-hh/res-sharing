package com.jony.controller;


import com.jony.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * sysUserRole表-控制器层
 *
 * @author jony
 * @since 2024-02-07 14:28:51
 */
@RestController
@RequestMapping("sysUserRole")
@RequiredArgsConstructor
public class SysUserRoleController {

    private final SysUserRoleService sysUserRoleService;
}

