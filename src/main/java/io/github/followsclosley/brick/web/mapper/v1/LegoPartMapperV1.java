package io.github.followsclosley.brick.web.mapper.v1;

import io.github.followsclosley.brick.data.entity.LegoPart;
import io.github.followsclosley.brick.web.dto.v1.LegoPartDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LegoPartMapperV1 {
    @Mapping(source = "legoCategory", target = "category")
    LegoPartDto toDto(LegoPart entity);
}
