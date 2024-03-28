package com.jony.config;

import cn.hutool.core.net.NetUtil;
import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import com.jony.utils.RedissonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@Order(0)
public class SystemInitConfig implements CommandLineRunner {

    @Resource
    private RedissonUtil redissonUtil;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 分布式锁Key
     */
    private static final String CACHE_ID_GENERATOR = "LOCK_ID_GENERATOR";

    /**
     * 最大机器号Key
     */
    private static final String CACHE_WORKERID_MAXID = "CACHE_WORKERID_MAXID";

    /**
     * 已分配的机器号Key
     */
    private static final String CACHE_ID_IP = "CACHE_ID_IP";

    @Override
    public void run(String... args) throws Exception {
        // 获取mac地址
        String macAddress = NetUtil.getLocalhost().getHostAddress();
        log.info("【{}】初始化 Unique_Worker_Id========开始", macAddress);
        boolean existWorkerId = redisTemplate.opsForHash().hasKey(CACHE_ID_IP, macAddress);
        // 若已缓存在缓存中，直接跳过不设置
        if (existWorkerId) {
            log.info("【{}】已配置 Unique_Worker_Id...", macAddress);
            return;
        }
        try {
            // 分布式锁等待120秒，执行时长最大120秒
            boolean locked = redissonUtil.tryLock(CACHE_ID_GENERATOR, 120, 120);
            if (!locked) {
                throw new RuntimeException(macAddress + "设置【分布式锁】失败");
            }
            ValueOperations<String, Object> stringOperation = redisTemplate.opsForValue();
            Boolean initWorkerId = stringOperation.setIfAbsent(CACHE_WORKERID_MAXID, 1);
            if (Boolean.FALSE.equals(initWorkerId)) {
                // 若已存在key，对最大的机器号自增1
                stringOperation.increment(CACHE_WORKERID_MAXID);
            }
            Integer workerId = (Integer) stringOperation.get(CACHE_WORKERID_MAXID);
            IdGeneratorOptions options = new IdGeneratorOptions(Objects.requireNonNull(workerId).shortValue());
            YitIdHelper.setIdGenerator(options);
            // 设置mac地址 - workerId 到hash结构
            redisTemplate.opsForHash().put(CACHE_ID_IP, macAddress, workerId);
            log.info("已配置Unique_Worker_Id,【{}】 - 【workerId: {}】", macAddress, workerId);
        } finally {
            redissonUtil.unlock(CACHE_ID_GENERATOR);
            log.info("【{}】初始化 Unique_Worker_Id========结束", macAddress);
        }

    }
}