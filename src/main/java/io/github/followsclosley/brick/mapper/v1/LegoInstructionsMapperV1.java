package io.github.followsclosley.brick.mapper.v1;

import io.github.followsclosley.brick.data.entity.LegoInstructions;
import io.github.followsclosley.brick.dto.v1.LegoInstructionsDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LegoInstructionsMapperV1 {
    LegoInstructionsDto toDto(LegoInstructions entity);
}
