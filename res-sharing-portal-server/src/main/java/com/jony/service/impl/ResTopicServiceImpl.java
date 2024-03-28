package com.jony.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jony.entity.ResTopic;
import com.jony.mapper.ResTopicMapper;
import com.jony.service.ResTopicService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * resTopic表-服务实现类
 *
 * @author jony
 * @since 2024-03-12 12:27:31
 */
@Service
public class ResTopicServiceImpl extends ServiceImpl<ResTopicMapper, ResTopic> implements ResTopicService {

    @Resource
    ResTopicMapper resTopicMapper;

    @Override
    public List<ResTopic> fetchWhole() {
        return this.list();
    }

    @Override
    public List<ResTopic> pagingQuery(Integer pageSize, Integer pageNum) {
        // 定义MP的分页对象 arg1:页面   arg2:行数
        IPage<ResTopic> page = new Page<>(pageNum, pageSize);
        IPage<ResTopic> resTopicIPage = resTopicMapper.selectPage(page, null);
        // 从分页对象中获取总记录数
        return resTopicIPage.getRecords();
    }

    @Override
    public ResTopic fetchById(Long id) {
        return this.getById(id);
    }
}

