package io.github.followsclosley.brick.web.dto.v1;

import jakarta.persistence.Id;
import lombok.*;

@Data
public class PartDtoV1 {
    @Id
    private String id;
    private String name;
    private CategoryDtoV1 category;
    private String material;
}