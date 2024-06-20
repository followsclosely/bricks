package io.github.followsclosley.brick.web.converter.v1;

import io.github.followsclosley.brick.data.Color;
import io.github.followsclosley.brick.data.Element;
import io.github.followsclosley.brick.web.converter.VersionedMapper;
import io.github.followsclosley.brick.web.dto.v1.ColorDtoV1;
import io.github.followsclosley.brick.web.dto.v1.ElementDtoV1;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface ElementMapperV1 extends VersionedMapper<Element, ElementDtoV1> {
    @Override
    default String getVersion() {
        return "1.0";
    }
}
