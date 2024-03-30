package com.jony.convert;

import com.jony.dto.UserThumbDTO;
import com.jony.entity.UserThumb;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserOperateConvert {

    UserOperateConvert INSTANCE = Mappers.getMapper(UserOperateConvert.class);

    UserThumb toUserThumb(UserThumbDTO userThumbDTO);

}
