package io.github.followsclosley.brick.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ManyToAny;

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

    @ManyToAny
    //@JoinTable(name="wall_inventory")
    private List<Piece> pieces;
}
