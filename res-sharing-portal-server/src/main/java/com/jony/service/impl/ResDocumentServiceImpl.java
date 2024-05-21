package com.jony.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jony.constant.IoConstant;
import com.jony.entity.ResDocument;
import com.jony.mapper.ResDocumentMapper;
import com.jony.service.ResDocumentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * resDocument表-服务实现类
 *
 * @author jony
 * @since 2024-03-12 12:26:17
 */
@Service
public class ResDocumentServiceImpl extends ServiceImpl<ResDocumentMapper, ResDocument> implements ResDocumentService {

    @Resource
    private ResDocumentMapper resDocumentMapper;

    @Override
    public List<ResDocument> fetchWhole() {
        LambdaQueryWrapper<ResDocument> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ResDocument::getPublishStatus, 1);
        return this.list(wrapper);
    }

    @Override
    public List<ResDocument> pagingQuery(Integer pageSize, Integer pageNum) {
        // 定义MP的分页对象 arg1:页面   arg2:行数
        IPage<ResDocument> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ResDocument> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ResDocument::getPublishStatus, 1);
        IPage<ResDocument> resDocumentIPage = resDocumentMapper.selectPage(page, wrapper);
        // 从分页对象中获取总记录数
        return resDocumentIPage.getRecords();
    }

    @Override
    public ResDocument fetchById(Long id) {
        return this.getById(id);
    }

    @Override
    public String addDocument(ResDocument resDocument) {
        boolean result = this.save(resDocument);
        if (result) {
            return IoConstant.saveSuccess;
        }
        return IoConstant.saveFail;
    }

}

