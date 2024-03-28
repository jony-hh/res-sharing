package com.jony.controller;


import com.jony.service.ResVideoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * resVideo表-控制器层
 *
 * @author jony
 * @since 2024-03-12 12:26:17
 */
@RestController
@RequestMapping("resVideo")
public class ResVideoController {

    @Resource
    private ResVideoService resVideoService;
}

