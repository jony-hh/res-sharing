package com.jony.convert;

import com.jony.entity.SysAuth;
import com.jony.entity.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SysUserConvert {

    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    @Mapping(source = "identifier", target = "username")
    @Mapping(source = "certificate", target = "password")
    @Mapping(source = "userId", target = "id")
    SysUser toResDocument(SysAuth resDocumentDTO);

}