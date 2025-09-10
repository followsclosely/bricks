package io.github.followsclosley.brick.mapper.v1;

import io.github.followsclosley.brick.data.entity.LegoInventory;
import io.github.followsclosley.brick.data.entity.LegoInventoryMinifig;
import io.github.followsclosley.brick.dto.v1.LegoInventoryDto;
import io.github.followsclosley.brick.dto.v1.LegoInventoryMinifigDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = LegoMinifigMapperV1.class)
public interface LegoInventoryMapperV1 {
    @Mapping(source = "legoSet", target = "set")
    LegoInventoryDto toDto(LegoInventory entity);
}
