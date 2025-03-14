package io.github.followsclosley.brick.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class LegoTheme {
    @Id
    private String id;
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private LegoTheme parent;

    @Transient
    private List<LegoTheme> children = new ArrayList<>();
}
