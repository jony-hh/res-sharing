package com.jony.controller;


import com.jony.service.UserRegisterLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * userRegisterLog表-控制器层
 *
 * @author jony
 * @since 2024-02-07 14:28:51
 */
@RestController
@RequestMapping("userRegisterLog")
@RequiredArgsConstructor
public class UserRegisterLogController {

    private  final UserRegisterLogService userRegisterLogService;
}

