package com.jony.controller;


import com.jony.api.CommonResult;
import com.jony.config.ElasticSearchConfigProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "资源检索")
@RequestMapping("es/search")
@RequiredArgsConstructor
public class ResSearchController {

    private final ElasticSearchConfigProperties elasticSearchConfigProperties;

    @Operation(summary = "只通过关键词搜索")
    @GetMapping("searchOnlyByKeyword")
    public CommonResult<?> searchOnlyByKeyword(String keyword) {

        return CommonResult.success(null);
    }

}
