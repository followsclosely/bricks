package io.github.followsclosley.brick.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Embeddable
@Data
public class LegoInventoryMinifig {

    @ManyToOne
    @JoinColumn(name = "part_id")
    private LegoMinifig legoMinifig;

    @Column(nullable = false)
    private int quantity;
}
