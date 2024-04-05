package com.jony.convert;

import com.jony.dto.QuestionDTO;
import com.jony.entity.UserQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserQuestionConvert {

    UserQuestionConvert INSTANCE = Mappers.getMapper(UserQuestionConvert.class);

    UserQuestion toUserQuestion(QuestionDTO questionDTO);
}
