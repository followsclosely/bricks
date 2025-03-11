package io.github.followsclosley.brick.data;

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
    private Boolean transparent;
}
