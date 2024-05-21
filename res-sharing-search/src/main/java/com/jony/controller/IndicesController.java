package com.jony.controller;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.cat.indices.IndicesRecord;
import com.jony.api.CommonResult;
import com.jony.api.IndexApi;
import com.jony.init.InitializeAndImport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "索引信息")
@RestController
@RequestMapping("es/index")
@RequiredArgsConstructor
public class IndicesController {

    private final IndexApi indexApi;
    private final InitializeAndImport initImport;


    @Operation(summary = "获取索引映射")
    @GetMapping("getMapping")
    public CommonResult<Map<String, Property>> getMapping(@RequestParam("indexName") String indexName) {
        TypeMapping indexMapping = indexApi.getMapping(indexName);
        return CommonResult.success(indexMapping.properties());

    }

    @Operation(summary = "查询索引细节")
    @GetMapping("queryIndexDetail")
    public CommonResult<Map<String, Property>> queryIndexContent(@RequestParam("indexName") String indexName) {
        Map<String, Property> stringPropertyMap = indexApi.queryIndexDetail(indexName);
        return CommonResult.success(stringPropertyMap);

    }

    @Operation(summary = "查询所有索引")
    @GetMapping("getAllIndices")
    public CommonResult<List<String>> getAllIndices() {
        List<IndicesRecord> allIndices = indexApi.getAllIndices();
        List<String> indexNames = allIndices.stream()
                .map(IndicesRecord::index)
                .collect(Collectors.toList());
        return CommonResult.success(indexNames);

    }


    @Operation(summary = "删除某个索引")
    @DeleteMapping("deleteIndex")
    public CommonResult<String> deleteIndex(@RequestParam("indexName") String indexName) {
        indexApi.deleteIndex(indexName);
        return CommonResult.success("删除成功");
    }


    @Operation(summary = "手动初始化索引")
    @GetMapping("initIndex")
    public CommonResult<String> initIndex() throws Exception {
        initImport.initAndImport();
        return CommonResult.success("初始化成功");
    }
}
