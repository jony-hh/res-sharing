package com.jony.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jony.entity.ResDocument;
import com.jony.mapper.ResDocumentMapper;
import com.jony.service.ResDocumentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * resDocument表-服务实现类
 *
 * @author jony
 * @since 2024-05-11 18:40:55
 */
@Service
public class ResDocumentServiceImpl extends ServiceImpl<ResDocumentMapper, ResDocument> implements ResDocumentService {

    @Resource
    private ResDocumentMapper resDocumentMapper;

    @Override
    public List<HashMap<String, Object>> getList(String query, Integer page, Integer size) {
        // 定义MP的分页对象 arg1:页面   arg2:行数
        IPage<ResDocument> pageing = new Page<>(page, size);
        LambdaQueryWrapper<ResDocument> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ResDocument::getPublishStatus, 0);
        IPage<ResDocument> resDocumentIPage = resDocumentMapper.selectPage(pageing, wrapper);
        // 从分页对象中获取总记录数
        HashMap<String, Object> hashMap1 = new HashMap<>();
        hashMap1.put("total", resDocumentIPage.getTotal());

        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("list", resDocumentIPage.getRecords());

        List<HashMap<String, Object>> resDocumentList = new ArrayList<>();
        resDocumentList.add(hashMap1);
        resDocumentList.add(hashMap2);


        return resDocumentList;
    }
}

