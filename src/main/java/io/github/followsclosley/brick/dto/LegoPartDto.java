package io.github.followsclosley.brick.dto;

import lombok.Data;

@Data
public class LegoPartDto {
    private String id;
    private String name;
    private LegoCategoryDto category;
    private String material;
}
