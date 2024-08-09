package io.github.followsclosley.brick.web.dto.v1;

import lombok.Data;

@Data
public class LegoInventoryMinifigDtoV1 {
    private MinifigDtoV1 legoMinifig;
    private int quantity;
}
