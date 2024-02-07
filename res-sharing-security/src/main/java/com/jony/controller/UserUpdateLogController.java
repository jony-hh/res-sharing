package com.jony.controller;


import com.jony.service.UserUpdateLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * userUpdateLog表-控制器层
 *
 * @author jony
 * @since 2024-02-07 14:28:52
 */
@RestController
@RequestMapping("userUpdateLog")
@RequiredArgsConstructor
public class UserUpdateLogController {

    private final UserUpdateLogService userUpdateLogService;
}

