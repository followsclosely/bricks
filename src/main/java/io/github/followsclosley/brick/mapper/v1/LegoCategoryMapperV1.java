package io.github.followsclosley.brick.mapper.v1;

import io.github.followsclosley.brick.data.entity.LegoCategory;
import io.github.followsclosley.brick.dto.v1.LegoCategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LegoCategoryMapperV1 {
    LegoCategoryDto toDto(LegoCategory entity);
}
