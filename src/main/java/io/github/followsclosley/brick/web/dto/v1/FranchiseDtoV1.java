package io.github.followsclosley.brick.web.dto.v1;

import io.github.followsclosley.brick.web.dto.FranchiseDto;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseDtoV1 implements FranchiseDto {
    private String id;
    private String name;
    private AddressDtoV1 address;
    private List<WallDtoV1> walls;
    private Instant lastUpdate;
}