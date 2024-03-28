package com.jony.convert;

import com.jony.dto.ResDocumentDTO;
import com.jony.entity.ResDocument;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResDocumentConvert {

    ResDocumentConvert INSTANCE = Mappers.getMapper(ResDocumentConvert.class);

    ResDocument toResDocument(ResDocumentDTO resDocumentDTO);

}
