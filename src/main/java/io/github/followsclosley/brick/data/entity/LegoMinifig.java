package io.github.followsclosley.brick.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
public class LegoMinifig {
    @Id
    private String id;
    private String name;
    private int partCount;
    private String imageUrl;
}
