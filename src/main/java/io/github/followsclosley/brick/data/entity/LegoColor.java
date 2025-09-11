package io.github.followsclosley.brick.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class LegoColor {
    @Id
    private String id;
    private String name;
    private String rgb;
    private String type;
    private Integer parts;
    private Integer yearFrom;
    private Integer yearTo;
}
