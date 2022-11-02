package io.github.followsclosley.brick.jpa.repository;

import io.github.followsclosley.brick.jpa.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FranchiseRepository extends JpaRepository<Franchise, String> {
//    @Modifying
//    @Query("UPDATE Wall w SET w.piece = :piece WHERE w.wal_id = wall_id AND w.bin = :bin")
//    void updateBin(@Param(value = "bin") int bin, @Param(value = "piece") Piece piece);
}
