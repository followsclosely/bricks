package io.github.followsclosley.brick.mapper.v1;

import io.github.followsclosley.brick.data.entity.LegoMinifig;
import io.github.followsclosley.brick.dto.v1.LegoMinifigDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LegoMinifigMapperV1 {
    LegoMinifigDto toDto(LegoMinifig entity);
}
