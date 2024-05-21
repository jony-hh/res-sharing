package com.jony.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jony.entity.ResVideoDetail;
import com.jony.mapper.ResVideoDetailMapper;
import com.jony.service.ResVideoDetailService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * resVideoDetail表-服务实现类
 *
 * @author jony
 * @since 2024-05-11 18:41:49
 */
@Service
public class ResVideoDetailServiceImpl extends ServiceImpl<ResVideoDetailMapper, ResVideoDetail> implements ResVideoDetailService {


    @Resource
    private ResVideoDetailMapper resVideoDetailMapper;

    @Override
    public List<HashMap<String, Object>> getList(String query, Integer page, Integer size) {
        // 定义MP的分页对象 arg1:页面   arg2:行数
        IPage<ResVideoDetail> pageing = new Page<>(page, size);
        LambdaQueryWrapper<ResVideoDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ResVideoDetail::getPublishStatus, 0);
        IPage<ResVideoDetail> videoDetailIPage = resVideoDetailMapper.selectPage(pageing, wrapper);
        // 从分页对象中获取总记录数
        HashMap<String, Object> hashMap1 = new HashMap<>();
        hashMap1.put("total",videoDetailIPage.getTotal());

        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("list", videoDetailIPage.getRecords());

        List<HashMap<String, Object>> videoDetailList = new ArrayList<>();
        videoDetailList.add(hashMap1);
        videoDetailList.add(hashMap2);
        return videoDetailList;
    }
}

