package com.jony.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jony.entity.UserThumb;
import org.apache.ibatis.annotations.Mapper;


/**
 * userThumb表-数据库访问层
 *
 * @author jony
 * @since 2024-03-30 14:55:54
 */
@Mapper
public interface UserThumbMapper extends BaseMapper<UserThumb> {

}

