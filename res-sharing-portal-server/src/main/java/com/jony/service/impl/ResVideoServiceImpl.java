package com.jony.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jony.constant.IoConstant;
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
        return this.list();
    }

    @Override
    public List<ResVideo> pagingQuery(Integer pageSize, Integer pageNum) {
        // 定义MP的分页对象 arg1:页面   arg2:行数
        IPage<ResVideo> page = new Page<>(pageNum, pageSize);
        IPage<ResVideo> reVidoeIPage = resVideoMapper.selectPage(page, null);
        // 从分页对象中获取总记录数
        return reVidoeIPage.getRecords();
    }

    @Override
    public ResVideo fetchById(Long id) {
        return this.getById(id);
    }

    @Override
    public String addVideo(ResVideo resVideo) {
        boolean result = this.save(resVideo);
        if (result) {
            return IoConstant.saveSuccess;
        }
        return IoConstant.saveFail;
    }
}

