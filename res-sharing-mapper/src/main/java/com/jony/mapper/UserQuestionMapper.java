package com.jony.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jony.entity.UserQuestion;
import org.apache.ibatis.annotations.Mapper;


/**
 * userQuestion表-数据库访问层
 *
 * @author jony
 * @since 2024-03-14 17:58:31
 */
@Mapper
public interface UserQuestionMapper extends BaseMapper<UserQuestion> {

}

