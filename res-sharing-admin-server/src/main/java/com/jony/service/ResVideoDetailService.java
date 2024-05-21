package com.jony.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jony.entity.ResVideoDetail;

import java.util.HashMap;
import java.util.List;


/**
 * resVideoDetail表-服务接口
 *
 * @author jony
 * @since 2024-05-11 18:41:49
 */
public interface ResVideoDetailService extends IService<ResVideoDetail> {

    List<HashMap<String, Object>> getList(String query, Integer page, Integer size);
}


