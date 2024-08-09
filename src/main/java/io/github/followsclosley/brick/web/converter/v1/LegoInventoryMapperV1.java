package io.github.followsclosley.brick.web.converter.v1;

import io.github.followsclosley.brick.data.LegoInventory;
import io.github.followsclosley.brick.web.converter.VersionedMapper;
import io.github.followsclosley.brick.web.dto.v1.LegoInventoryDtoV1;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface LegoInventoryMapperV1 extends VersionedMapper<LegoInventory, LegoInventoryDtoV1> {
    @Override
    default String getVersion() {
        return "1.0";
    }
}
