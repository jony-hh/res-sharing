package com.jony.convert;

import com.jony.dto.ResVideoDTO;
import com.jony.dto.ResVideoDetailDTO;
import com.jony.entity.ResVideo;
import com.jony.entity.ResVideoDetail;
import com.jony.vo.CourseVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResVideoConvert {

    ResVideoConvert INSTANCE = Mappers.getMapper(ResVideoConvert.class);

    ResVideo toResVideo(ResVideoDTO resVideoDTO);

    ResVideoDetail toResVideoDetail(ResVideoDetailDTO resVideoDetailDTO);

    CourseVO toCourseVO(ResVideo resVideo);
}
