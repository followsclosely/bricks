package io.github.followsclosley.brick.mapper.v1;

import io.github.followsclosley.brick.data.entity.LegoColor;
import io.github.followsclosley.brick.data.entity.LegoMinifig;
import io.github.followsclosley.brick.dto.v1.LegoColorDto;
import io.github.followsclosley.brick.dto.v1.LegoMinifigDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LegoMinifigMapperV1 {
    LegoMinifigDto toDto(LegoMinifig entity);
}
