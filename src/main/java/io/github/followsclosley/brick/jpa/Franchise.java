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
public class Franchise {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "franchise_id")
    private List<Wall> walls;

    private Address address;
}
