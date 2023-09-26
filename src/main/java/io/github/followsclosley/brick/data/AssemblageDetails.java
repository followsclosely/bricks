package io.github.followsclosley.brick.data;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AssemblageDetails implements Serializable {
    private List<AssemblagePiece> pieces;
}
