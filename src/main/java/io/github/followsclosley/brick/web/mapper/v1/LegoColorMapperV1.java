package io.github.followsclosley.brick.web.mapper.v1;

import io.github.followsclosley.brick.data.entity.LegoColor;
import io.github.followsclosley.brick.web.dto.v1.LegoColorDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LegoColorMapperV1 {
    LegoColorDto toDto(LegoColor entity);
}
