package io.github.followsclosley.brick.web.converter.v1;

import io.github.followsclosley.brick.data.LegoTheme;
import io.github.followsclosley.brick.web.converter.VersionedMapper;
import io.github.followsclosley.brick.web.dto.v1.ThemeDtoV1;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface ThemeMapperV1 extends VersionedMapper<LegoTheme, ThemeDtoV1> {
    @Override
    default String getVersion() {
        return "1.0";
    }
}
