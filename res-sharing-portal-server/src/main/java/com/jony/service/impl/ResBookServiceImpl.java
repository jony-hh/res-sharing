package com.jony.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jony.constant.IoConstant;
import com.jony.entity.ResBook;
import com.jony.mapper.ResBookMapper;
import com.jony.service.ResBookService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * resBook表-服务实现类
 *
 * @author jony
 * @since 2024-03-14 19:03:53
 */
@Service
public class ResBookServiceImpl extends ServiceImpl<ResBookMapper, ResBook> implements ResBookService {


    @Resource
    ResBookMapper resBookMapper;

    @Override
    public List<ResBook> fetchWhole() {
        return this.list();
    }

    @Override
    public List<ResBook> pagingQuery(Integer pageSize, Integer pageNum) {
        // 定义MP的分页对象 arg1:页面   arg2:行数
        IPage<ResBook> page = new Page<>(pageNum, pageSize);
        IPage<ResBook> resDocumentIPage = resBookMapper.selectPage(page, null);
        // 从分页对象中获取总记录数
        return resDocumentIPage.getRecords();
    }

    @Override
    public String addWikiBook(ResBook book) {
        boolean result = this.save(book);
        if (result) {
            return IoConstant.saveSuccess;
        }
        return IoConstant.saveFail;
    }

    @Override
    public ResBook fetchById(Long id) {
        return this.getById(id);
    }
}

