package io.github.followsclosley.brick.web.mapper.v1;

import io.github.followsclosley.brick.data.entity.LegoInventoryPart;
import io.github.followsclosley.brick.web.dto.v1.LegoInventoryPartDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LegoInventoryPartMapperV1 {
    @Mapping(source = "legoPart", target = "part")
    @Mapping(source = "legoColor", target = "color")
    LegoInventoryPartDto toDto(LegoInventoryPart entity);
}
