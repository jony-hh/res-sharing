package com.jony.controller;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.cat.indices.IndicesRecord;
import com.jony.api.CommonResult;
import com.jony.api.IndexApi;
import com.jony.config.ElasticSearchConfigProperties;
import io.micrometer.core.instrument.search.Search;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
