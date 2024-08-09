package io.github.followsclosley.brick.web.converter.v1;


import io.github.followsclosley.brick.data.LegoMinifig;
import io.github.followsclosley.brick.web.converter.VersionedMapper;
import io.github.followsclosley.brick.web.dto.v1.MinifigDtoV1;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface MinifigMapperV1 extends VersionedMapper<LegoMinifig, MinifigDtoV1> {
    @Override
    default String getVersion() {
        return "1.0";
    }
}
