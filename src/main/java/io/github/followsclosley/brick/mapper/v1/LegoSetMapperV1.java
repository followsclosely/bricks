package io.github.followsclosley.brick.mapper.v1;

import io.github.followsclosley.brick.data.entity.LegoSet;
import io.github.followsclosley.brick.dto.v1.LegoSetDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LegoSetMapperV1 {
    LegoSetDto toDto(LegoSet entity);
}
