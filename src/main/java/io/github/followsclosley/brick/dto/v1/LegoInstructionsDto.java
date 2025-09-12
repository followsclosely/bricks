package io.github.followsclosley.brick.dto.v1;

import lombok.Data;

@Data
public class LegoInstructionsDto {
    private String id;
    private String name;
    private Integer releaseYear;

    private Integer weightInGrams;
    private String dimensions;

    private LegoCategoryDto legoCategory;
}
