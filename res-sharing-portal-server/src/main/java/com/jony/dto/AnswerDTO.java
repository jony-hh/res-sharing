package com.jony.dto;

import lombok.Data;

@Data
public class AnswerDTO {

    Long questionId;

    Long userId;

    String summary;

    String content;
}