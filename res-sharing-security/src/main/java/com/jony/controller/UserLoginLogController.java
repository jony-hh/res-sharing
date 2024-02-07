package com.jony.controller;


import com.jony.service.UserLoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * userLoginLog表-控制器层
 *
 * @author jony
 * @since 2024-02-07 14:28:51
 */
@RestController
@RequestMapping("userLoginLog")
@RequiredArgsConstructor
public class UserLoginLogController {

    private final UserLoginLogService userLoginLogService;
}

