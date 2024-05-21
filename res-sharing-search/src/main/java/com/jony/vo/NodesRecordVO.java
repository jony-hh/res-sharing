package com.jony.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodesRecordVO {
    // 节点IP
    private String ip;
    // 堆内存使用百分比
    private String heapPercent;
    // 物理内存使用百分比
    private String ramPercent;
    // CPU使用情况
    private String cpu;
    // 1分钟平均负载
    private String load1m;
    // 5分钟平均负载
    private String load5m;
    // 15分钟平均负载
    private String load15m;
    // 节点角色
    private String nodeRole;
    // 是否为主节点
    private String master;
    // 节点名称
    private String name;
}
