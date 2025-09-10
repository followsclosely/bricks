package io.github.followsclosley.brick.web.dto.v1;

import lombok.Data;

@Data
public class LegoElementDto {
    private String id;
    private LegoPartDto part;
    private LegoColorDto color;
    private String design;


}
