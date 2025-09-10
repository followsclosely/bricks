package io.github.followsclosley.brick.dto.v1;

import lombok.Data;

@Data
public class LegoInventoryMinifigDto {
    private LegoMinifigDto minifig;
    private int quantity;
}
