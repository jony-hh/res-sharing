package com.jony.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jony.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;


/**
 * sysMenu表-数据库访问层
 *
 * @author jony
 * @since 2024-02-07 14:28:50
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

}

