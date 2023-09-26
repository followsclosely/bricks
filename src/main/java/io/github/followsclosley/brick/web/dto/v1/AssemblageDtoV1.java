package io.github.followsclosley.brick.web.dto.v1;

import io.github.followsclosley.brick.data.AssemblageDetails;
import io.github.followsclosley.brick.web.dto.AssemblageDto;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AssemblageDtoV1 implements AssemblageDto {
    private String id;
    private String name;
    private AssemblageDetailsDtoV1 details;
}
