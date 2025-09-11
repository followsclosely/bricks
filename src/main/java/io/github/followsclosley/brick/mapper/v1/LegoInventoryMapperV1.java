package io.github.followsclosley.brick.mapper.v1;

import io.github.followsclosley.brick.data.entity.LegoInventory;
import io.github.followsclosley.brick.dto.v1.LegoInventoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {LegoMinifigMapperV1.class, LegoPartMapperV1.class, LegoInventoryMinifigMapperV1.class, LegoInventoryPartMapperV1.class})
public interface LegoInventoryMapperV1 {
    @Mapping(source = "legoSet", target = "set")
    @Mapping(source = "parts", target = "parts")
    @Mapping(source = "minifigs", target = "minifigs")
    LegoInventoryDto toDto(LegoInventory entity);
}
