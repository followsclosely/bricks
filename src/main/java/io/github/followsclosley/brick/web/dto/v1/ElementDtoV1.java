package io.github.followsclosley.brick.web.dto.v1;

import lombok.Data;

@Data
public class ElementDtoV1 {
    private String id;
    private PartDtoV1 legoPart;
    private ColorDtoV1 legoColor;
    private String design;
}