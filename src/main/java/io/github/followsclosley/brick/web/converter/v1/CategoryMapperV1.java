package io.github.followsclosley.brick.web.converter.v1;

import io.github.followsclosley.brick.data.LegoCategory;
import io.github.followsclosley.brick.web.converter.VersionedMapper;
import io.github.followsclosley.brick.web.dto.v1.CategoryDtoV1;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface CategoryMapperV1 extends VersionedMapper<LegoCategory, CategoryDtoV1> {
    @Override
    default String getVersion() {
        return "1.0";
    }
}
