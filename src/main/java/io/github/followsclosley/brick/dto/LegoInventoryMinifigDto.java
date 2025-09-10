package io.github.followsclosley.brick.dto;

import lombok.Data;

@Data
public class LegoInventoryMinifigDto {
    private LegoMinifigDto legoMinifig;
    private int quantity;
}
