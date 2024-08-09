package io.github.followsclosley.brick.data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LegoInventoryPart {

    @ManyToOne
    @JoinColumn(name = "part_id")
    private LegoPart legoPart;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private LegoColor color;

    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private boolean spare;
}
