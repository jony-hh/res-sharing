package com.jony.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jony.constant.IoConstant;
import com.jony.entity.UserQuestion;
import com.jony.mapper.UserQuestionMapper;
import com.jony.service.UserQuestionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * userQuestion表-服务实现类
 *
 * @author jony
 * @since 2024-03-14 17:59:43
 */
@Service
public class UserQuestionServiceImpl extends ServiceImpl<UserQuestionMapper, UserQuestion> implements UserQuestionService {

    @Resource
    UserQuestionMapper userQuestionMapper;

    @Override
    public List<UserQuestion> fetchWhole() {
        return this.list().stream()
                .sorted(Comparator.comparing(UserQuestion::getUpdatedTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<UserQuestion> pagingQuery(Integer pageSize, Integer pageNum) {
        // 定义MP的分页对象 arg1:页面   arg2:行数
        IPage<UserQuestion> page = new Page<>(pageNum, pageSize);
        IPage<UserQuestion> resDocumentIPage = userQuestionMapper.selectPage(page, null);
        // 从分页对象中获取总记录数
        return resDocumentIPage.getRecords();
    }

    @Override
    public UserQuestion fetchById(Long id) {
        return this.getById(id);
    }

    @Override
    public String addQuestion(UserQuestion question) {
        boolean save = this.save(question);
        if (save) {
            return IoConstant.saveSuccess;
        }
        return IoConstant.saveFail;
    }
}

