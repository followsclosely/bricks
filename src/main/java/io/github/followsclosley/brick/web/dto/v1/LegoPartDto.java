package io.github.followsclosley.brick.web.dto.v1;

import lombok.Data;

@Data
public class LegoPartDto {
    private String id;
    private String name;
    private LegoCategoryDto category;
    private String material;
}
