package io.github.followsclosley.brick.jpa.repository;

import io.github.followsclosley.brick.jpa.Franchise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseRepository extends JpaRepository<Franchise, String> {
    Page<Franchise> findByNameContainingIgnoreCase(String name, Pageable pageable);
//    @Modifying
//    @Query("UPDATE Wall w SET w.piece = :piece WHERE w.wal_id = wall_id AND w.bin = :bin")
//    void updateBin(@Param(value = "bin") int bin, @Param(value = "piece") Piece piece);
}
