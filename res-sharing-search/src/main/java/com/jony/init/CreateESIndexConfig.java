package com.jony.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * ES初始化创建索引
 */
@Component
@Order(value = 1)
@Slf4j
@RequiredArgsConstructor
public class CreateESIndexConfig implements CommandLineRunner {

    private final InitializeAndImport initImport;

    /**
     * 项目启动的时候，如果elasticsearch已经存有索引，则不做任何操作，如果没有索引，则新建索引
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        initImport.initAndImport();
    }
}
