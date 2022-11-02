package io.github.followsclosley.brick.web.dto.v1;

import io.github.followsclosley.brick.web.dto.CategoryDto;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDtoV1 implements CategoryDto {
    private String id;
    private String name;
}