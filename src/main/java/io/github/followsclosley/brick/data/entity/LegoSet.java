package io.github.followsclosley.brick.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class LegoSet {
    @Id
    private String id;
    private String name;
    private Integer releaseYear;

    private Integer weightInGrams;
    private String dimensions;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private LegoCategory legoCategory;
}
