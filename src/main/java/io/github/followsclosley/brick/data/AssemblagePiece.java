package io.github.followsclosley.brick.data;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AssemblagePiece {
    private boolean owned;
    private Piece piece;
}
