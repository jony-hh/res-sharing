package com.jony.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jony.entity.ResBook;

import java.util.List;


/**
 * resBook表-服务接口
 *
 * @author jony
 * @since 2024-03-14 19:03:53
 */
public interface ResBookService extends IService<ResBook> {

    List<ResBook> fetchWhole();

    List<ResBook> pagingQuery(Integer pageSize, Integer pageNum);

    String addWikiBook(ResBook book);

    ResBook fetchById(Long id);
}


