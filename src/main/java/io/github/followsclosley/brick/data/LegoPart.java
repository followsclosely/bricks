package io.github.followsclosley.brick.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class LegoPart {
    @Id
    private String id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private LegoCategory legoCategory;
    private String material;
}
