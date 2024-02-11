package com.jony.api;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cat.nodes.NodesRecord;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author jony
 * @description 获取所有索引信息
 */
@Slf4j
@Component
public class NodeApi {
    @Resource
    private ElasticsearchClient elasticsearchClient;

    /**
     * 获取所有索引信息
     *
     * @return NodesRecord列表
     * @throws IOException 异常信息
     */
    public List<NodesRecord> getAllNodes() throws IOException {
        List<NodesRecord> nodesRecords = elasticsearchClient.cat().nodes().valueBody();
        log.info("node size is:{}", nodesRecords.size());

        return nodesRecords;
    }
}
