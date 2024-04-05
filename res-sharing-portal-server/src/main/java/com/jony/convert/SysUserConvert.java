package com.jony.convert;

import com.jony.dto.UserUpdateDTO;
import com.jony.entity.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SysUserConvert {

    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUser toSysUser(UserUpdateDTO userUpdateDTO);

    void updateTargetFromSource(UserUpdateDTO source, @MappingTarget SysUser target);
}
