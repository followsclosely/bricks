package io.github.followsclosley.brick.mapper.v1;

import io.github.followsclosley.brick.data.entity.LegoTheme;
import io.github.followsclosley.brick.dto.v1.LegoThemeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LegoThemeMapperV1 {
    LegoThemeDto toDto(LegoTheme entity);
}
