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
    private int releaseYear;
    @ManyToOne
    @JoinColumn(name = "theme_id")
    private LegoTheme legoTheme;
    private int partCount;
    private String imageUrl;
}
