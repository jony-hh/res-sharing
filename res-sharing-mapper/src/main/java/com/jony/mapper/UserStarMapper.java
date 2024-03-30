package com.jony.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jony.entity.UserStar;
import org.apache.ibatis.annotations.Mapper;


/**
 * userStar表-数据库访问层
 *
 * @author jony
 * @since 2024-03-30 14:55:54
 */
@Mapper
public interface UserStarMapper extends BaseMapper<UserStar> {

}

