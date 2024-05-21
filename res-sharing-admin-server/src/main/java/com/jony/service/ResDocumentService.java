package com.jony.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jony.entity.ResDocument;

import java.util.HashMap;
import java.util.List;


/**
 * resDocument表-服务接口
 *
 * @author jony
 * @since 2024-05-11 18:40:55
 */
public interface ResDocumentService extends IService<ResDocument> {

    List<HashMap<String, Object>> getList(String query, Integer page, Integer size);
}


