package io.github.followsclosley.brick.web.dto.v1;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PieceDtoV1 {
    private String id;
    private ElementDtoV1 element;
    private ColorDtoV1 color;
}