package com.jony.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jony.entity.ResVideo;
import com.jony.mapper.ResVideoMapper;
import com.jony.service.ResVideoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * resVideo表-服务实现类
 *
 * @author jony
 * @since 2024-05-11 18:41:28
 */
@Service
public class ResVideoServiceImpl extends ServiceImpl<ResVideoMapper, ResVideo> implements ResVideoService {

    @Resource
    private ResVideoMapper resVideoMapper;

    @Override
    public List<HashMap<String, Object>> getList(String query, Integer page, Integer size) {
        // 定义MP的分页对象 arg1:页面   arg2:行数
        IPage<ResVideo> pageing = new Page<>(page, size);
        LambdaQueryWrapper<ResVideo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ResVideo::getPublishStatus, 0);
        IPage<ResVideo> videoIPage = resVideoMapper.selectPage(pageing, wrapper);
        // 从分页对象中获取总记录数
        HashMap<String, Object> hashMap1 = new HashMap<>();
        hashMap1.put("total", videoIPage.getTotal());

        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("list", videoIPage.getRecords());

        List<HashMap<String, Object>> videoList = new ArrayList<>();
        videoList.add(hashMap1);
        videoList.add(hashMap2);
        return videoList;
    }
}

