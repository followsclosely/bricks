package io.github.followsclosley.brick.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LegoTheme {
    @Id
    private String id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private LegoTheme parent;
}
