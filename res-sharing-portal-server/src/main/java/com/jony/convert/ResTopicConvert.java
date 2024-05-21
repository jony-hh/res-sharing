package com.jony.convert;

import com.jony.dto.ResTopicDTO;
import com.jony.entity.ResTopic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResTopicConvert {

    ResTopicConvert INSTANCE = Mappers.getMapper(ResTopicConvert.class);

    ResTopic toResTopic(ResTopicDTO resTopicDTO);

}
