package com.jony.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jony.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * sysUser表-数据库访问层
 *
 * @author jony
 * @since 2024-02-07 14:32:03
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}

