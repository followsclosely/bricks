package io.github.followsclosley.brick.dto;

import lombok.Data;

@Data
public class LegoInventoryPartDto {
    private LegoPartDto legoPart;
    private LegoColorDto color;
    private int quantity;
    private boolean spare;
}
