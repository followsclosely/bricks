package io.github.followsclosley.brick.web.dto.v1;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ElementDtoV1 {
    private String id;
    private String name;
    private CategoryDtoV1 category;
}