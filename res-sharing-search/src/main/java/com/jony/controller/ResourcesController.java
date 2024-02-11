package com.jony.controller;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import com.jony.api.CommonResult;
import com.jony.api.IndexApi;
import com.jony.config.ElasticSearchConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("es")
public class ResourcesController {

    @Autowired
    private IndexApi indexApi;

    @Autowired
    private ElasticSearchConfigProperties elasticSearchConfigProperties;

    @GetMapping("queryIndexDetail")
    public CommonResult<Map<String, Property>> queryIndexContent(){
        String indexName = elasticSearchConfigProperties.getIndex();
        try {
            Map<String, Property> stringPropertyMap = indexApi.queryIndexDetail(indexName);
            return CommonResult.success(stringPropertyMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
