package io.github.followsclosley.brick.web.mapper.v1;

import io.github.followsclosley.brick.data.entity.LegoElement;
import io.github.followsclosley.brick.web.dto.v1.LegoElementDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LegoElementMapperV1 {
    @Mapping(source = "legoPart", target = "part")
    @Mapping(source = "legoColor", target = "color")
    LegoElementDto toDto(LegoElement entity);
}
