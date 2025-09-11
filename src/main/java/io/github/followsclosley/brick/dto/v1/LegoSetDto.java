package io.github.followsclosley.brick.dto.v1;

import io.github.followsclosley.brick.data.entity.LegoTheme;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class LegoSetDto {
    private String id;
    private String name;
    private Integer releaseYear;

    private LegoTheme legoTheme;
    private Integer partCount;
    private String imageUrl;

    private Collection<LegoInventoryPartDto> parts = new ArrayList<>();
    private Collection<LegoInventoryMinifigDto> minifigs = new ArrayList<>();
}
