package io.github.followsclosley.brick.web.dto.v1;

import lombok.Data;

@Data
public class LegoInventoryMinifigDto {
    private LegoMinifigDto minifig;
    private int quantity;
}
