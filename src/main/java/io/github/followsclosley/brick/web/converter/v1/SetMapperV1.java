package io.github.followsclosley.brick.web.converter.v1;


import io.github.followsclosley.brick.data.LegoSet;
import io.github.followsclosley.brick.web.converter.VersionedMapper;
import io.github.followsclosley.brick.web.dto.v1.SetDtoV1;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface SetMapperV1 extends VersionedMapper<LegoSet, SetDtoV1> {
    @Override
    default String getVersion() {
        return "1.0";
    }
}
