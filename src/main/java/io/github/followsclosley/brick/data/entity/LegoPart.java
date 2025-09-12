package io.github.followsclosley.brick.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class LegoPart {
    @Id
    private String id;
    @Column(length = 1000)
    private String name;
    private String alternate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private LegoCategory legoCategory;
}
