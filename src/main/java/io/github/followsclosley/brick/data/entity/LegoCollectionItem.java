package io.github.followsclosley.brick.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

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
