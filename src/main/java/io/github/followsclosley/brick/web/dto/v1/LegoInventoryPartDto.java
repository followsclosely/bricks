package io.github.followsclosley.brick.web.dto.v1;

import lombok.Data;

@Data
public class LegoInventoryPartDto {
    private LegoPartDto part;
    private LegoColorDto color;
    private int quantity;
    private boolean spare;
}
