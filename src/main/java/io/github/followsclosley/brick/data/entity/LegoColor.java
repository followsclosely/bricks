package io.github.followsclosley.brick.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString()
public class LegoColor {
    @Id
    private String id;
    private String name;
    private String rgb;
    private Boolean transparent;
}
