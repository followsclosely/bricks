package io.github.followsclosley.brick.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wall {
    @Id private String id;
    @Column(length = 1000)
    private String name;

    @ManyToMany
    @JoinTable(name="wall_inventory")
    @OrderColumn(name = "bin", nullable = false)
    private List<Piece> pieces;
}
