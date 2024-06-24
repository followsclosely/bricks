package io.github.followsclosley.brick.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryPart {
    private Part part;
    private Color color;
    private int quantity;
    private boolean spare;
}
