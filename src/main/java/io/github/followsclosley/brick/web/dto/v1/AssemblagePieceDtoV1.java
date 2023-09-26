package io.github.followsclosley.brick.web.dto.v1;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AssemblagePieceDtoV1 {
    private boolean owned;
    private PieceDtoV1 piece;
}
