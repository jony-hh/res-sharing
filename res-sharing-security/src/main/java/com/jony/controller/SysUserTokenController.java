package com.jony.controller;


import com.jony.service.SysUserTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * sysUserToken表-控制器层
 *
 * @author jony
 * @since 2024-02-07 14:28:51
 */
@RestController
@RequestMapping("sysUserToken")
@RequiredArgsConstructor
public class SysUserTokenController {

    private final SysUserTokenService sysUserTokenService;
}

