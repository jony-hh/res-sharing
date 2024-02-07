package com.jony.controller;


import com.jony.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * sysMenu表-控制器层
 *
 * @author jony
 * @since 2024-02-07 14:28:50
 */
@RestController
@RequestMapping("sysMenu")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService sysMenuService;
}

