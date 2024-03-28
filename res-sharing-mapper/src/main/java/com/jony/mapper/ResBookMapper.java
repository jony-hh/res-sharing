package com.jony.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jony.entity.ResBook;
import org.apache.ibatis.annotations.Mapper;


/**
 * resBook表-数据库访问层
 *
 * @author jony
 * @since 2024-03-14 19:03:34
 */
@Mapper
public interface ResBookMapper extends BaseMapper<ResBook> {

}

