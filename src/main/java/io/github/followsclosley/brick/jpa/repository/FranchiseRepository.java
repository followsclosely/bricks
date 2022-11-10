package io.github.followsclosley.brick.jpa.repository;

import io.github.followsclosley.brick.jpa.Franchise;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public interface FranchiseRepository extends JpaRepository<Franchise, String> {

    Page<Franchise> findByNameContainingIgnoreCase(String name, Pageable pageable);
//    @Modifying
//    @Query("UPDATE Wall w SET w.piece = :piece WHERE w.wal_id = wall_id AND w.bin = :bin")
//    void updateBin(@Param(value = "bin") int bin, @Param(value = "piece") Piece piece);
}
