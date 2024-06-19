package io.github.followsclosley.brick.web.dto.v1;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AssemblageDtoV1 {
    private String id;
    private String name;
    private AssemblageDetailsDtoV1 details;
}
