package com.jony.api;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ShardStatistics;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.cat.indices.IndicesRecord;
import co.elastic.clients.elasticsearch.indices.*;
import co.elastic.clients.elasticsearch.indices.get_mapping.IndexMappingRecord;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.jony.exception.ErrorCode;
import com.jony.exception.ServerException;
import jakarta.annotation.Resource;
import jakarta.json.Json;
import jakarta.json.stream.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author jony
 * @description 索引api
 */
@Slf4j
@Component
public class IndexApi {
    @Resource
    private ElasticsearchClient elasticsearchClient;

    /**
     * 执行refresh或flush操作
     *
     * @param response 相应对象
     * @return 操作是否成功，true成功 false失败
     */
    private boolean doOperation(ShardStatistics response) {
        int failedShards = response.failures().size();
        if (failedShards == response.total().intValue()) {
            log.info("ES索引刷新失败 {}", response.failures());
            return false;
        } else if (failedShards > 0) {
            log.info("ES索引刷新部分分片失败 {}", response.failures());
        }
        log.info("ES索引刷新成功");
        return true;
    }

    /**
     * 创建索引
     *
     * @param indexName 索引名
     * @return 是否创建成功
     * @throws IOException 异常信息
     */
    public boolean createIndex(String indexName) {
        CreateIndexResponse createIndexResponse = null;
        try {
            if (isExistedIndex(indexName)) {
                deleteIndex(indexName);
            }
            // 写法比RestHighLevelClient更加简洁
            createIndexResponse = elasticsearchClient.indices().create(c -> c.index(indexName));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        log.info("{} 索引创建是否成功: {}", indexName, createIndexResponse.acknowledged());

        return createIndexResponse.acknowledged();
    }

    /**
     * 判断index是否存在
     *
     * @param indexName 索引名
     * @return 是否存在
     * @throws IOException 异常信息
     */
    public boolean isExistedIndex(String indexName) {
        BooleanResponse booleanResponse = null;
        try {
            booleanResponse = elasticsearchClient.indices().exists(e -> e.index(indexName));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        log.info("{} 索引是否存在: {}", indexName, booleanResponse.value());

        return booleanResponse.value();
    }

    /**
     * 删除index
     *
     * @param indexName 索引名
     * @return 是否删除成功
     * @throws IOException 异常信息
     */
    public boolean deleteIndex(String indexName) {
        DeleteIndexResponse deleteIndexResponse = null;
        try {
            deleteIndexResponse = elasticsearchClient.indices().delete(d -> d.index(indexName));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        log.info("{} 索引是否被删除: {}", indexName, deleteIndexResponse.acknowledged());

        return deleteIndexResponse.acknowledged();
    }

    /**
     * 创建索引 - 指定mapping
     *
     * @param indexName 索引名
     * @return 是否创建成功
     * @throws IOException 异常信息
     */
    public boolean createIndexWithMapping(String indexName, TypeMapping typeMapping) {
        if (isExistedIndex(indexName)) {
            deleteIndex(indexName);
        }

        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = elasticsearchClient
                    .indices()
                    .create(createIndexRequest -> createIndexRequest
                            .index(indexName)
                            // 用 lambda 的方式 下面的 mapping 会覆盖上面的 mapping
                            .mappings(typeMapping));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        log.info("{} 索引创建是否成功: {}", indexName, createIndexResponse.acknowledged());

        return createIndexResponse.acknowledged();
    }

    /**
     * 创建索引 - 用json脚本创建mapping
     *
     * @param indexName     索引名
     * @param mappingScript mapping的json脚本
     * @return 是否创建成功
     * @throws IOException 异常信息
     */
    public boolean createIndexWithMapping(String indexName, String mappingScript) {
        if (isExistedIndex(indexName)) {
            deleteIndex(indexName);
        }

        JsonpMapper mapper = elasticsearchClient._transport().jsonpMapper();
        JsonParser parser = Json.createParser(new StringReader(mappingScript));

        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = elasticsearchClient
                    .indices()
                    .create(createIndexRequest -> createIndexRequest
                            .index(indexName)
                            .mappings(TypeMapping._DESERIALIZER.deserialize(parser, mapper)));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        log.info("{} 索引创建是否成功: {}", indexName, createIndexResponse.acknowledged());

        return createIndexResponse.acknowledged();
    }

    /**
     * 查询index
     *
     * @param indexName 索引名
     * @return GetIndexResponse对象
     * @throws IOException 异常信息
     */
    public GetIndexResponse queryIndex(String indexName) {
        GetIndexResponse getIndexResponse = null;
        try {
            getIndexResponse = elasticsearchClient.indices().get(i -> i.index(indexName));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        log.info("{} 索引信息: {}", indexName, getIndexResponse);

        return getIndexResponse;
    }

    /**
     * 查询index相关信息
     *
     * @param indexName 索引名
     * @return GetIndexResponse对象
     * @throws IOException 异常信息
     */
    public Map<String, Property> queryIndexDetail(String indexName) {
        GetIndexResponse getIndexResponse =
                null;
        try {
            getIndexResponse = elasticsearchClient.indices().get(getIndexRequest -> getIndexRequest.index(indexName));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        Map<String, Property> properties = Objects.requireNonNull(
                        Objects.requireNonNull(getIndexResponse.get(indexName)).mappings())
                .properties();

        for (Map.Entry<String, Property> entry : properties.entrySet()) {
            log.info(
                    "{} 索引的详细信息为: key: {}, property: {}",
                    indexName,
                    entry.getKey(),
                    properties.get(entry.getKey())._kind());
        }

        return properties;
    }

    /**
     * 获取所有索引信息
     *
     * @return IndicesRecord列表
     * @throws IOException 异常信息
     */
    public List<IndicesRecord> getAllIndices() {
        List<IndicesRecord> indicesRecords = null;
        try {
            indicesRecords = elasticsearchClient.cat().indices().valueBody();
        } catch (IOException e) {
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        log.info("size is:{}", indicesRecords.size());

        return indicesRecords;
    }

    /**
     * 获取Mapping信息
     *
     * @param indexName 索引名
     * @return TypeMapping对象
     * @throws IOException 异常信息
     */
    public TypeMapping getMapping(String indexName) {
        GetMappingResponse getMappingResponse = null;
        try {
            getMappingResponse = elasticsearchClient.indices().getMapping();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        TypeMapping typeMapping = getMappingResponse.result().get(indexName).mappings();

        log.info("typeMapping is:{}", typeMapping);

        return typeMapping;
    }

    /**
     * 获取所有Mapping信息
     *
     * @return Map<String, TypeMapping>对象，key就是索引名，value是TypeMapping对象
     * @throws IOException 异常信息
     */
    public Map<String, TypeMapping> getAllMappings() {
        Map<String, IndexMappingRecord> indexMappingRecordMap =
                null;
        try {
            indexMappingRecordMap = elasticsearchClient.indices().getMapping().result();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        Map<String, TypeMapping> result = new HashMap<>(indexMappingRecordMap.size());

        for (Map.Entry<String, IndexMappingRecord> entry : indexMappingRecordMap.entrySet()) {
            String key = entry.getKey();
            TypeMapping typeMapping = indexMappingRecordMap.get(key).mappings();
            log.info("索引的详细信息为: key: {}, property: {}", key, typeMapping);

            result.put(key, typeMapping);
        }

        return result;
    }

    /**
     * 索引refresh
     *
     * @param indexName 索引名
     * @return refresh是否成功，true成功 false失败
     * @throws IOException 异常信息
     */
    public boolean refresh(String indexName) {
        RefreshResponse response = null;
        try {
            response = elasticsearchClient.indices().refresh(request -> request.index(indexName));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        return doOperation(response.shards());
    }

    /**
     * 索引flush
     *
     * @param indexName 索引名
     * @return flush是否成功，true成功 false失败
     * @throws IOException 异常信息
     */
    public boolean flush(String indexName) {
        FlushResponse response = null;
        try {
            response = elasticsearchClient.indices().flush(request -> request.index(indexName));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        return doOperation(response.shards());
    }
}
