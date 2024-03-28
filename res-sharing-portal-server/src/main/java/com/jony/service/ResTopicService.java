package com.jony.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jony.entity.ResTopic;

import java.util.List;


/**
 * resTopic表-服务接口
 *
 * @author jony
 * @since 2024-03-12 12:27:31
 */
public interface ResTopicService extends IService<ResTopic> {

    List<ResTopic> fetchWhole();

    List<ResTopic> pagingQuery(Integer pageSize, Integer pageNum);

    ResTopic fetchById(Long id);

}


