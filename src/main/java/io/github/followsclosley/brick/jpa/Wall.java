package io.github.followsclosley.brick.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wall {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(length = 1000)
    private String name;

    @ManyToMany
    @JoinTable(name="wall_inventory")
    @OrderColumn(name = "bin", nullable = false)
    private List<Piece> pieces;
}
