package io.github.followsclosley.brick.web.converter.v1;

import io.github.followsclosley.brick.data.LegoElement;
import io.github.followsclosley.brick.web.converter.VersionedMapper;
import io.github.followsclosley.brick.web.dto.v1.ElementDtoV1;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface ElementMapperV1 extends VersionedMapper<LegoElement, ElementDtoV1> {
    @Override
    default String getVersion() {
        return "1.0";
    }
}
