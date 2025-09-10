package io.github.followsclosley.brick.web.mapper.v1;

import io.github.followsclosley.brick.data.entity.LegoInventory;
import io.github.followsclosley.brick.data.entity.LegoInventoryMinifig;
import io.github.followsclosley.brick.data.entity.LegoInventoryPart;
import io.github.followsclosley.brick.web.dto.v1.LegoInventoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {LegoMinifigMapperV1.class, LegoPartMapperV1.class, LegoInventoryMinifigMapperV1.class, LegoInventoryPartMapperV1.class})
public interface LegoInventoryMapperV1 {
    @Mapping(source = "legoSet", target = "set")
    @Mapping(source = "parts", target = "parts")
    @Mapping(source = "minifigs", target = "minifigs")
    LegoInventoryDto toDto(LegoInventory entity);
}
