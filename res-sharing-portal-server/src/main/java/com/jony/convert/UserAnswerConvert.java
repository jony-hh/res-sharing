package com.jony.convert;

import com.jony.dto.AnswerDTO;
import com.jony.entity.UserAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserAnswerConvert {

    UserAnswerConvert INSTANCE = Mappers.getMapper(UserAnswerConvert.class);

    UserAnswer toUserAnswer(AnswerDTO answerDTO);
}
