package io.github.followsclosley.brick.web.dto.v1;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AssemblageDetailsDtoV1 {
    private List<AssemblagePieceDtoV1> pieces;
}
