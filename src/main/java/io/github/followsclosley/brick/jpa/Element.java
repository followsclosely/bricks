package io.github.followsclosley.brick.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Element {

    @Id
    private String id;
    @Column(length = 1000)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
