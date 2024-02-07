package com.jony.controller;


import com.jony.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * sysRole表-控制器层
 *
 * @author jony
 * @since 2024-02-07 14:28:50
 */
@RestController
@RequestMapping("sysRole")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;
}

