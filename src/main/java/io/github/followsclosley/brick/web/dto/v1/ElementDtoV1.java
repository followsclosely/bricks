package io.github.followsclosley.brick.web.dto.v1;

import lombok.*;

@Data
public class ElementDtoV1 {
    private String id;
    private PartDtoV1 part;
    private ColorDtoV1 color;
    private String design;
}