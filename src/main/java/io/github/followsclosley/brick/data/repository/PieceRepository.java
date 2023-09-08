package io.github.followsclosley.brick.data.repository;

import io.github.followsclosley.brick.data.Piece;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PieceRepository extends JpaRepository<Piece, String> {
    Page<Piece> findByElementNameContainingIgnoreCase(String name, Pageable pageable);
}
