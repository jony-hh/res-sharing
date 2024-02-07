package com.jony.controller;


import com.jony.service.SysRoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * sysRoleMenu表-控制器层
 *
 * @author jony
 * @since 2024-02-07 14:28:51
 */
@RestController
@RequestMapping("sysRoleMenu")
@RequiredArgsConstructor
public class SysRoleMenuController {

    private final SysRoleMenuService sysRoleMenuService;
}

