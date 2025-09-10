package io.github.followsclosley.brick.web.mapper.v1;

import io.github.followsclosley.brick.data.entity.LegoInventoryMinifig;
import io.github.followsclosley.brick.web.dto.v1.LegoInventoryMinifigDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = LegoMinifigMapperV1.class)
public interface LegoInventoryMinifigMapperV1 {
    @Mapping(source = "legoMinifig", target = "minifig")
    LegoInventoryMinifigDto toDto(LegoInventoryMinifig entity);
}
