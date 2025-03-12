package io.github.followsclosley.brick.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Data
public class LegoElement {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private LegoPart legoPart;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private LegoColor legoColor;

    private String design;
}
