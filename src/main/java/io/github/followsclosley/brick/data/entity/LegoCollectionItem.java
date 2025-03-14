package io.github.followsclosley.brick.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Embeddable
@Data
public class LegoCollectionItem {

    @ManyToOne
    @JoinColumn(name = "element_id")
    private LegoElement legoElement;

    @ManyToOne
    @JoinColumn(name = "lego_set_id")
    private LegoSet legoSet;

    @Column
    private int quantity;

    @Column
    private String comment;

    private int index;
}
