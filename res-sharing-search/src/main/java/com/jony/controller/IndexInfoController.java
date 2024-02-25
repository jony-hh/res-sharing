package com.jony.controller;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.cat.indices.IndicesRecord;
import co.elastic.clients.elasticsearch.cat.nodes.NodesRecord;
import com.jony.api.CommonResult;
import com.jony.api.IndexApi;
import com.jony.api.NodeApi;
import com.jony.config.ElasticSearchConfigProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "索引信息")
@RestController
@RequestMapping("es/index")
@RequiredArgsConstructor
public class IndexInfoController {

    private final IndexApi indexApi;
    private final NodeApi nodeApi;
    private final ElasticSearchConfigProperties esConfig;

    @GetMapping("getAllNodes")
    public CommonResult<?> getAllNodes() {
        List<NodesRecord> allNodes = nodeApi.getAllNodes();
        return CommonResult.success(allNodes);
    }

    @Operation(summary = "获取索引映射")
    @GetMapping("getMapping")
    public CommonResult<Map<String, Property>> getMapping() {
        String indexName = esConfig.getIndex();
        TypeMapping indexMapping = indexApi.getMapping(indexName);
        return CommonResult.success(indexMapping.properties());

    }

    @Operation(summary = "查询索引细节")
    @GetMapping("queryIndexDetail")
    public CommonResult<Map<String, Property>> queryIndexContent() {
        String indexName = esConfig.getIndex();
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

}
