package com.jony.controller.res;


import com.jony.api.CommonResult;
import com.jony.entity.ResTopic;
import com.jony.service.ResTopicService;
import com.jony.service.impl.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * resTopic表-控制器层
 *
 * @author jony
 * @since 2024-03-12 12:27:31
 */
@RestController
@RequestMapping("resTopic")
@Tag(name = "元素话题接口")
@Slf4j
public class ResTopicController {

    @Resource
    private ResTopicService resTopicService;

    @Resource
    FileService fileService;

    @GetMapping("/fetchWhole")
    @Operation(summary = "取得所有话题元素数据")
    public CommonResult<?> fetchWhole() {
        List<ResTopic> documentList = resTopicService.fetchWhole();
        return CommonResult.success(documentList);
    }

    @GetMapping("/pagingQuery")
    @Operation(summary = "分页查询话题元素数据")
    public CommonResult<?> pagingQuery(@RequestParam("page_size") Integer pageSize, @RequestParam("page_num") Integer pageNum) {
        List<ResTopic> documentList = resTopicService.pagingQuery(pageSize, pageNum);
        return CommonResult.success(documentList);
    }

    @GetMapping("/fetchById")
    @Operation(summary = "根据id查询单个话题元素数据")
    public CommonResult<?> fetchById(@RequestParam("id") Long id) {
        ResTopic documentList = resTopicService.fetchById(id);
        return CommonResult.success(documentList);
    }

}

