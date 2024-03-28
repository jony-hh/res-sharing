package com.jony.convert;

import com.jony.dto.ResVideoDTO;
import com.jony.entity.ResVideo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResVideoConvert {

    ResVideoConvert INSTANCE = Mappers.getMapper(ResVideoConvert.class);

    ResVideo toResVideo(ResVideoDTO resVideoDTO);
}
