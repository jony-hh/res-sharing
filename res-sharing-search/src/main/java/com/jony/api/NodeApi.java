package com.jony.api;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cat.nodes.NodesRecord;
import com.jony.exception.ErrorCode;
import com.jony.exception.ServerException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author jony
 * @description 节点api
 */
@Slf4j
@Component
public class NodeApi {
    @Resource
    private ElasticsearchClient elasticsearchClient;

    /**
     * 获取【elasticsearch】节点信息
     *
     * @return NodesRecord列表
     * @throws IOException 异常信息
     */
    public List<NodesRecord> getAllNodes() {
        List<NodesRecord> nodesRecords = null;
        try {
            nodesRecords = elasticsearchClient.cat().nodes().valueBody();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        log.info("node size is:{}", nodesRecords.size());

        return nodesRecords;
    }
}
