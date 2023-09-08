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
public class Piece {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "element_id")
    private Element element;
    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;
}
