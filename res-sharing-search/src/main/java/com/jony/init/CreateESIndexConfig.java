package com.jony.init;

import com.jony.api.IndexApi;
import com.jony.config.ElasticSearchConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * ES初始化创建索引
 */
@Component
@Order(value = 1)
@Slf4j
public class CreateESIndexConfig implements CommandLineRunner {

    @Autowired
    private IndexApi indexApi;

    @Autowired
    private ElasticSearchConfigProperties elasticSearchConfigProperties;

    // json文件存储路径为：src/main/resources/es-settings/es_settings.json
    @Value("classpath:es-settings/test1.json")
    private Resource esSetting;

    /**
     * 项目启动的时候，如果elasticsearch已经存有索引，则不做任何操作，如果没有索引，则新建索引
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("[ES]开始创建索引{}...", "test_index");
        String indexName = elasticSearchConfigProperties.getIndex();
        boolean existedIndex = indexApi.isExistedIndex(indexName);
        // 有索引直接返回
        if (existedIndex) {
            log.info(indexName + "索引已创建，不需要初始化");
            return;
        }
        // 没有索引，读取json文件内的字符内容
        InputStream stream = esSetting.getInputStream();
        String esSettingStr = IOUtils.toString(stream, Charset.forName("utf-8"));
        boolean isCreated = indexApi.createIndexWithMapping(indexName, esSettingStr);
        if (isCreated) {
            log.info(indexName + "索引初始化成功");
        }
    }
}
