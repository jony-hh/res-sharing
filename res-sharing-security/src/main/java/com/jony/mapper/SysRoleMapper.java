package com.jony.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jony.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * sysRole表-数据库访问层
 *
 * @author jony
 * @since 2024-02-07 14:28:50
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> findRoleInfoByUserId(@Param("userId") Long userId);
}

