package com.jony.controller;

import co.elastic.clients.elasticsearch.cat.nodes.NodesRecord;
import com.jony.api.CommonResult;
import com.jony.api.NodeApi;
import com.jony.vo.NodesRecordVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "es节点信息")
@RequestMapping("es/node")
@RequiredArgsConstructor
public class NodeController {

    private final NodeApi nodeApi;

    @Operation(summary = "获取所有节点信息")
    @GetMapping("getAllNodes")
    public CommonResult<?> getAllNodes() {
        List<NodesRecord> allNodes = nodeApi.getAllNodes();
        List<NodesRecordVO> nodeRecordVOS = new ArrayList<>();
        allNodes.forEach(node -> {
            NodesRecordVO nodesRecordVO = new NodesRecordVO();
            nodesRecordVO.setIp(node.ip());
            nodesRecordVO.setHeapPercent(node.heapPercent());
            nodesRecordVO.setRamPercent(node.ramPercent());
            nodesRecordVO.setCpu(node.cpu());
            nodesRecordVO.setLoad1m(node.load1m());
            nodesRecordVO.setLoad5m(node.load5m());
            nodesRecordVO.setLoad15m(node.load15m());
            nodesRecordVO.setNodeRole(node.nodeRole());
            nodesRecordVO.setMaster(node.master());
            nodesRecordVO.setName(node.name());
            nodeRecordVOS.add(nodesRecordVO);
        });

        return CommonResult.success(nodeRecordVOS);
    }

}
