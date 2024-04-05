package com.jony.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jony.entity.ResComment;
import org.apache.ibatis.annotations.Mapper;


/**
 * resComment表-数据库访问层
 *
 * @author jony
 * @since 2024-04-05 15:39:07
 */
@Mapper
public interface ResCommentMapper extends BaseMapper<ResComment> {

}

