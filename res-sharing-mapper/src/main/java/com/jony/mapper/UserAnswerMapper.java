package com.jony.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jony.entity.UserAnswer;
import org.apache.ibatis.annotations.Mapper;


/**
 * userAnswer表-数据库访问层
 *
 * @author jony
 * @since 2024-03-30 14:54:58
 */
@Mapper
public interface UserAnswerMapper extends BaseMapper<UserAnswer> {

}

