package io.github.followsclosley.brick.jpa;

import jakarta.persistence.*;

@Entity
public class AssemblagePiece {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private int sort;

    @Column
    private boolean owned;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("piece_id")
    private Piece piece;


}
