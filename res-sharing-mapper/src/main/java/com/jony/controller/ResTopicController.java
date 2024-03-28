package com.jony.controller;


import com.jony.service.ResTopicService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * resTopic表-控制器层
 *
 * @author jony
 * @since 2024-03-12 12:27:08
 */
@RestController
@RequestMapping("resTopic")
public class ResTopicController {

    @Resource
    private ResTopicService resTopicService;
}

