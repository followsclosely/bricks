package io.github.followsclosley.brick.mapper.v1;

import io.github.followsclosley.brick.data.entity.LegoInventoryMinifig;
import io.github.followsclosley.brick.dto.v1.LegoInventoryMinifigDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = LegoMinifigMapperV1.class)
public interface LegoInventoryMinifigMapperV1 {
    @Mapping(source = "legoMinifig", target = "minifig")
    LegoInventoryMinifigDto toDto(LegoInventoryMinifig entity);
}
