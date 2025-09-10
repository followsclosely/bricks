package io.github.followsclosley.brick.dto;

import lombok.Data;

@Data
public class LegoElementDto {
    private String id;
    private LegoPartDto part;
    private LegoColorDto color;
    private String design;


}
