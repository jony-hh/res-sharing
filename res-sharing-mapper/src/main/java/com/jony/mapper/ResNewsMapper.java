package com.jony.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jony.entity.ResNews;
import org.apache.ibatis.annotations.Mapper;


/**
 * resNews表-数据库访问层
 *
 * @author jony
 * @since 2024-03-12 12:19:12
 */
@Mapper
public interface ResNewsMapper extends BaseMapper<ResNews> {

}

