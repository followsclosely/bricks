package io.github.followsclosley.brick.web.dto.v1;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WallDtoV1 {
    private String id;
    private String name;
    private List<PieceDtoV1> pieces;
}