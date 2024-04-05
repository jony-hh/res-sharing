package com.jony.convert;

import com.jony.dto.ResCommentDTO;
import com.jony.entity.ResComment;
import com.jony.vo.ResCommentVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResCommentConvert {

    ResCommentConvert INSTANCE = Mappers.getMapper(ResCommentConvert.class);

    @Mapping(source = "userId", target = "uid")
    ResCommentVO toResCommentVO(ResComment resComment);

    @Mapping(source = "uid", target = "userId")
    ResComment toResComment(ResCommentDTO resCommentDTO);

}
