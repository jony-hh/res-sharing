package com.jony.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jony.entity.UserUpdateLog;


/**
 * userUpdateLog表-数据库访问层
 *
 * @author jony
 * @since 2024-02-07 14:28:52
 */
@Mapper
public interface UserUpdateLogMapper extends BaseMapper<UserUpdateLog> {

}

