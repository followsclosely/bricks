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
public class PieceDtoV1 implements FranchiseDto {
    private String id;

    private ElementDtoV1 element;
    private ColorDtoV1 color;
}