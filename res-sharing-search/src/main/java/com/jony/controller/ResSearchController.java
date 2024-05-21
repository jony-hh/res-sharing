package com.jony.controller;


import com.jony.api.CommonResult;
import com.jony.api.DocumentApi;
import com.jony.api.QueryApi;
import com.jony.constant.IndicesName;
import com.jony.entity.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@Tag(name = "资源检索")
@RequestMapping("es/search")
@RequiredArgsConstructor
public class ResSearchController {

    private final DocumentApi documentApi;
    private final QueryApi queryApi;

    @Operation(summary = "获取所有文档")
    @GetMapping("getAllDocuments")
    public CommonResult<?> getAllDocuments(@RequestParam String indexName) throws IOException {
        List<UserQuestion> allDocument = documentApi.getAllDocument(indexName, UserQuestion.class);
        return CommonResult.success(allDocument);
    }

    @SneakyThrows
    @Operation(summary = "只通过关键词搜索")
    @GetMapping("searchOnlyByKeyword")
    public CommonResult<?> searchOnlyByKeyword(@RequestParam String type, @RequestParam String keyword) {
        List<String> searchFields = Arrays.stream(new String[]{"courseName", "title", "summary"}).toList();
        String sortedField = "id";
        int fromIndex = 0;
        int pageSize = 10;
        boolean isDesc = true;
        String indexName;
        List<?> dataList = null;
        switch (type) {
            case "course" -> {
                indexName = IndicesName.videoIndexName;
                List<?> resVideoList = queryApi.multiMatchQuery(indexName, keyword, searchFields, sortedField, fromIndex, pageSize, isDesc, ResVideo.class);
                dataList = resVideoList;
            }
            case "document" -> {
                indexName = IndicesName.documentIndexName;
                List<?> resDocumentList = queryApi.multiMatchQuery(indexName, keyword, searchFields, sortedField, fromIndex, pageSize, isDesc, ResDocument.class);
                dataList = resDocumentList;
            }
            case "topic" -> {
                indexName = IndicesName.topicIndexName;
                List<?> resTopicList = queryApi.multiMatchQuery(indexName, keyword, searchFields, sortedField, fromIndex, pageSize, isDesc, ResTopic.class);
                dataList = resTopicList;
            }
            case "question" -> {
                indexName = IndicesName.questionIndexName;
                dataList = queryApi.multiMatchQuery(indexName, keyword, searchFields, sortedField, fromIndex, pageSize, isDesc, UserQuestion.class);
            }
            case "wiki" -> {
                indexName = IndicesName.bookIndexName;
                dataList = queryApi.multiMatchQuery(indexName, keyword, searchFields, sortedField, fromIndex, pageSize, isDesc, ResBook.class);
            }
            default -> {
                return CommonResult.failed("参数错误");
            }
        }

        return CommonResult.success(dataList);
    }

}
