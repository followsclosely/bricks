package io.github.followsclosley.brick.jpa.repository;

import io.github.followsclosley.brick.jpa.Piece;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PieceRepository extends JpaRepository<Piece, String> {
    Page<Piece> findByElementNameContainingIgnoreCase(String name, Pageable pageable);
}
