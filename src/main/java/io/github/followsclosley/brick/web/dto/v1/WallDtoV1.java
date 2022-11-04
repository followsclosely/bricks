package io.github.followsclosley.brick.web.dto.v1;

import io.github.followsclosley.brick.web.dto.WallDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WallDtoV1 implements WallDto {
    private String id;
    private String name;

    private List<PieceDtoV1> pieces;
}