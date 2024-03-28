package com.jony.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jony.entity.UserQuestion;

import java.util.List;


/**
 * userQuestion表-服务接口
 *
 * @author jony
 * @since 2024-03-14 17:59:43
 */
public interface UserQuestionService extends IService<UserQuestion> {

    List<UserQuestion> fetchWhole();

    List<UserQuestion> pagingQuery(Integer pageSize, Integer pageNum);

    UserQuestion fetchById(Long id);

    String addQuestion(UserQuestion question);
}


