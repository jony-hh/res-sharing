package com.jony.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jony.entity.ResDocument;

import java.util.List;


/**
 * resDocument表-服务接口
 *
 * @author jony
 * @since 2024-03-12 12:26:17
 */
public interface ResDocumentService extends IService<ResDocument> {

    List<ResDocument> fetchWhole();

    List<ResDocument> pagingQuery(Integer pageSize, Integer pageNum);

    ResDocument fetchById(Long id);

    String addDocument(ResDocument resDocument);
}


