package io.github.followsclosley.brick.jpa;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "assemblage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Assemblage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(length = 1000)
    private String name;

//    @OneToMany
//    @JoinTable(name = "wall_inventory")
//    @OrderColumn(name = "bin", nullable = false)
//    private List<AssemblagePiece> pieces;
}
