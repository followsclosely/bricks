package io.github.followsclosley.brick.data;

import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity(name = "assemblage")
public class Assemblage {
    @Id
    private String id;
    private String name;
    private List<AssemblagePiece> pieces;
}
