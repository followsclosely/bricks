package io.github.followsclosley.brick.web.dto.v1;

import io.github.followsclosley.brick.web.dto.FranchiseDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WallDtoV1 implements FranchiseDto {
    private String id;
    private String name;

    private List<PieceDtoV1> pieces;
}