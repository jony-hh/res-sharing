package com.jony.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jony.entity.ResVideo;

import java.util.List;

/**
 * resVideo表-服务接口
 *
 * @author jony
 * @since 2024-03-12 12:27:08
 */
public interface ResVideoService extends IService<ResVideo> {

    List<ResVideo> fetchWhole();

    List<ResVideo> pagingQuery(Integer pageSize, Integer pageNum);

    ResVideo fetchById(Long id);

    ResVideo addVideo(ResVideo resVideo);

    List<ResVideo> fetchByUserId(Long userId);
}


