package com.jony.dto;

import lombok.Data;

@Data
public class WikiBookDTO {

    Long userId;

    String coverUrl;

    String summary;

    String content;
}
