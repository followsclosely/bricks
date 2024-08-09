package io.github.followsclosley.brick.web.dto.v1;

import lombok.Data;

@Data
public class LegoInventoryPartDtoV1 {
    private PartDtoV1 legoPart;
    private ColorDtoV1 color;
    private int quantity;
    private boolean spare;
}
