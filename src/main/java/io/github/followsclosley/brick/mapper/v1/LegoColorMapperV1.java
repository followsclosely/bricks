package io.github.followsclosley.brick.mapper.v1;

import io.github.followsclosley.brick.data.entity.LegoColor;
import io.github.followsclosley.brick.dto.v1.LegoColorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LegoColorMapperV1 {
    @Mapping(source = "yearFrom", target = "from")
    @Mapping(source = "yearTo", target = "to")
    LegoColorDto toDto(LegoColor entity);
}
