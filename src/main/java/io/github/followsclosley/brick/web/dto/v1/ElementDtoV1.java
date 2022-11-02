package io.github.followsclosley.brick.web.dto.v1;

import io.github.followsclosley.brick.web.dto.ElementDto;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ElementDtoV1 implements ElementDto {
    private String id;
    private String name;
    private CategoryDtoV1 category;
}