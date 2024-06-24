package io.github.followsclosley.brick.web.converter.v1;

import io.github.followsclosley.brick.data.Part;
import io.github.followsclosley.brick.web.converter.VersionedMapper;
import io.github.followsclosley.brick.web.dto.v1.PartDtoV1;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface PartMapperV1 extends VersionedMapper<Part, PartDtoV1> {
    @Override
    default String getVersion() {
        return "1.0";
    }
}
