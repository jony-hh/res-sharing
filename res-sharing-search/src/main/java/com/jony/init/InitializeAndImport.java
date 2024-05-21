package com.jony.init;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jony.api.DocumentApi;
import com.jony.api.IndexApi;
import com.jony.constant.IndicesName;
import com.jony.entity.*;
import com.jony.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitializeAndImport {

    private final IndexApi indexApi;
    private final DocumentApi documentApi;

    private final ResVideoMapper resVideoMapper;
    private final ResDocumentMapper resDocumentMapper;
    private final ResTopicMapper resTopicMapper;
    private final ResBookMapper resBookMapper;
    private final UserQuestionMapper userQuestionMapper;

    // json文件存储路径为：src/main/resources/es-settings/es_settings.json
    @Value("classpath:es-init/res_video_index.json")
    private Resource videoIndex;

    @Value("classpath:es-init/res_document_index.json")
    private Resource documentIndex;

    @Value("classpath:es-init/res_topic_index.json")
    private Resource topicIndex;

    @Value("classpath:es-init/res_book_index.json")
    private Resource bookIndex;

    @Value("classpath:es-init/user_question_index.json")
    private Resource questionIndex;


    public void initAndImport() throws Exception {
        log.info("开始初始化索引、同步数据...");

        // 创建视频索引，读取json文件内的字符内容
        String video = IOUtils.toString(videoIndex.getInputStream(), StandardCharsets.UTF_8);
        if (indexApi.createIndexWithMapping(IndicesName.videoIndexName, video)) {
            // 查库
            LambdaQueryWrapper<ResVideo> wrapper = new LambdaQueryWrapper<>();
            List<ResVideo> resVideos = resVideoMapper.selectList(wrapper);
            // 数据同步到ES
            documentApi.batchAddDocument(IndicesName.videoIndexName,resVideos);
        }

        // 创建文档索引，读取json文件内的字符内容
        String document = IOUtils.toString(documentIndex.getInputStream(), StandardCharsets.UTF_8);
        if (indexApi.createIndexWithMapping(IndicesName.documentIndexName, document)) {
            LambdaQueryWrapper<ResDocument> wrapper = new LambdaQueryWrapper<>();
            List<ResDocument> resDocuments = resDocumentMapper.selectList(wrapper);
            documentApi.batchAddDocument(IndicesName.documentIndexName,resDocuments);
        }

        // 创建话题索引，读取json文件内的字符内容
        String topic = IOUtils.toString(topicIndex.getInputStream(), StandardCharsets.UTF_8);
        if (indexApi.createIndexWithMapping(IndicesName.topicIndexName, topic)) {
            LambdaQueryWrapper<ResTopic> wrapper = new LambdaQueryWrapper<>();
            List<ResTopic> resTopics = resTopicMapper.selectList(wrapper);
            documentApi.batchAddDocument(IndicesName.topicIndexName,resTopics);
        }

        // 创建图书索引，读取json文件内的字符内容
        String book = IOUtils.toString(bookIndex.getInputStream(), StandardCharsets.UTF_8);
        if (indexApi.createIndexWithMapping(IndicesName.bookIndexName, book)) {
            LambdaQueryWrapper<ResBook> wrapper = new LambdaQueryWrapper<>();
            List<ResBook> resBooks = resBookMapper.selectList(wrapper);
            documentApi.batchAddDocument(IndicesName.bookIndexName,resBooks);
        }

        // 创建用户提问索引，读取json文件内的字符内容
        String question = IOUtils.toString(questionIndex.getInputStream(), StandardCharsets.UTF_8);
        if (indexApi.createIndexWithMapping(IndicesName.questionIndexName, question)) {
            LambdaQueryWrapper<UserQuestion> wrapper = new LambdaQueryWrapper<>();
            List<UserQuestion> questions = userQuestionMapper.selectList(wrapper);
            documentApi.batchAddDocument(IndicesName.questionIndexName,questions);
        }

        log.info("Elasticsearch 索引初始化完成, 数据同步完成...");
    }


    /**
     * 定时任务，每10分钟执行一次初始化索引、同步数据操作
     *
     * @throws Exception
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void scheduledInitAndImport() throws Exception {
        // initAndImport();
    }

}
