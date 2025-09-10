package io.github.followsclosley.brick.web.dto.v1;

import lombok.Data;

import java.util.Set;


@Data
public class LegoInventoryDto {

    private String id;
    private String version;

    private LegoSetDto set;
    private Set<LegoInventoryPartDto> parts;
    private Set<LegoInventoryMinifigDto> minifigs;
}
