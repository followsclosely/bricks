package io.github.followsclosley.brick.web.dto.v1;

import io.github.followsclosley.brick.web.dto.FranchiseDto;
import lombok.*;

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
}