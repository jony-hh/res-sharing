package com.jony.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jony.entity.ResVideo;
import com.jony.mapper.ResVideoMapper;
import com.jony.service.ResVideoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * resVideo表-服务实现类
 *
 * @author jony
 * @since 2024-03-12 12:27:08
 */
@Service
public class ResVideoServiceImpl extends ServiceImpl<ResVideoMapper, ResVideo> implements ResVideoService {


    @Resource
    ResVideoMapper resVideoMapper;

    @Override
    public List<ResVideo> fetchWhole() {
        LambdaQueryWrapper<ResVideo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ResVideo::getPublishStatus, 1);
        return this.list(wrapper);
    }

    @Override
    public List<ResVideo> pagingQuery(Integer pageSize, Integer pageNum) {
        // 定义MP的分页对象 arg1:页面   arg2:行数
        IPage<ResVideo> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ResVideo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ResVideo::getPublishStatus, 1);
        IPage<ResVideo> reVidoeIPage = resVideoMapper.selectPage(page, wrapper);
        // 从分页对象中获取总记录数
        return reVidoeIPage.getRecords();
    }

    @Override
    public ResVideo fetchById(Long id) {
        return this.getById(id);
    }

    @Override
    public ResVideo addVideo(ResVideo resVideo) {
        boolean result = this.save(resVideo);
        if (result) {
            return resVideo;
        }
        return null;
    }

    @Override
    public List<ResVideo> fetchByUserId(Long userId) {
        LambdaQueryWrapper<ResVideo> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(ResVideo::getUserId, userId);
        lambdaQueryWrapper.eq(ResVideo::getPublishStatus, 1);
        return this.list(lambdaQueryWrapper);
    }
}

