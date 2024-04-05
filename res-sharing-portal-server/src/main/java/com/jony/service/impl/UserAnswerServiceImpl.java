package com.jony.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jony.constant.IoConstant;
import com.jony.entity.UserAnswer;
import com.jony.mapper.UserAnswerMapper;
import com.jony.service.UserAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * userAnswer表-服务实现类
 *
 * @author jony
 * @since 2024-04-05 17:09:41
 */
@Service
@RequiredArgsConstructor
public class UserAnswerServiceImpl extends ServiceImpl<UserAnswerMapper, UserAnswer> implements UserAnswerService {

    private final UserAnswerMapper userAnswerMapper;


    @Override
    public List<UserAnswer> fetchWhole(Long questionId) {
        LambdaQueryWrapper<UserAnswer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserAnswer::getQuestionId,questionId);
        lambdaQueryWrapper.eq(UserAnswer::getDeleted,0);
        List<UserAnswer> userAnswers = userAnswerMapper.selectList(lambdaQueryWrapper);
        return userAnswers;
    }

    @Override
    public String addAnswer(UserAnswer userAnswer) {
        boolean save = this.save(userAnswer);
        if (save) {
            return IoConstant.saveSuccess;
        }
        return IoConstant.saveFail;
    }
}

