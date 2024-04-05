package com.jony.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jony.entity.UserAnswer;

import java.util.List;


/**
 * userAnswer表-服务接口
 *
 * @author jony
 * @since 2024-04-05 17:09:41
 */
public interface UserAnswerService extends IService<UserAnswer> {

    List<UserAnswer> fetchWhole(Long answerId);

    String addAnswer(UserAnswer userAnswer);
}


