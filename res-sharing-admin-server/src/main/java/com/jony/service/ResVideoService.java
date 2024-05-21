package com.jony.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jony.entity.ResVideo;

import java.util.HashMap;
import java.util.List;


/**
 * resVideo表-服务接口
 *
 * @author jony
 * @since 2024-05-11 18:41:28
 */
public interface ResVideoService extends IService<ResVideo> {

    List<HashMap<String, Object>> getList(String query, Integer page, Integer size);
}


