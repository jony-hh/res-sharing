package com.jony.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jony.entity.SysAuth;
import org.apache.ibatis.annotations.Param;


/**
 * sysAuth表-数据库访问层
 *
 * @author jony
 * @since 2024-02-07 13:06:29
 */
@Mapper
public interface SysAuthMapper extends BaseMapper<SysAuth> {

    SysAuth findByUserAuthIdentifierAndAndUserAuthType(@Param("identifier") String identifier,@Param("authType") String authType);
}

