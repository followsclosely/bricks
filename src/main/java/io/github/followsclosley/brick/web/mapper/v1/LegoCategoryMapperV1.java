package io.github.followsclosley.brick.web.mapper.v1;

import io.github.followsclosley.brick.data.entity.LegoCategory;
import io.github.followsclosley.brick.web.dto.v1.LegoCategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LegoCategoryMapperV1 {
    LegoCategoryDto toDto(LegoCategory entity);
}
