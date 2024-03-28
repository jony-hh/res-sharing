package com.jony.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jony.entity.UserRegisterLog;
import org.apache.ibatis.annotations.Mapper;


/**
 * userRegisterLog表-数据库访问层
 *
 * @author jony
 * @since 2024-02-07 14:28:52
 */
@Mapper
public interface UserRegisterLogMapper extends BaseMapper<UserRegisterLog> {

}

