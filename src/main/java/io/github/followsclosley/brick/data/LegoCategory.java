package io.github.followsclosley.brick.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class LegoCategory {
    @Id
    private String id;
    private String name;
}