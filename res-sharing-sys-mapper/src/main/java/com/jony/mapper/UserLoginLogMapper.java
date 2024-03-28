package com.jony.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jony.entity.UserLoginLog;
import org.apache.ibatis.annotations.Mapper;


/**
 * userLoginLog表-数据库访问层
 *
 * @author jony
 * @since 2024-02-07 14:28:51
 */
@Mapper
public interface UserLoginLogMapper extends BaseMapper<UserLoginLog> {

}

