package io.github.followsclosley.brick.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PieceRepository extends JpaRepository<Piece, String> {
    //Page<Piece> findByPieceNameContainingIgnoreCase(String name, Pageable pageable);
}
