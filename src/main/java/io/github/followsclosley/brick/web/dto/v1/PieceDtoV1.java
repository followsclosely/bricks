package io.github.followsclosley.brick.web.dto.v1;

import io.github.followsclosley.brick.web.dto.PieceDto;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PieceDtoV1 implements PieceDto {
    private String id;
    private ElementDtoV1 element;
    private ColorDtoV1 color;
}