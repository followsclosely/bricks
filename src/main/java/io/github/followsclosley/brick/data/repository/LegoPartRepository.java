package io.github.followsclosley.brick.data.repository;

import io.github.followsclosley.brick.data.LegoPart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegoPartRepository extends JpaRepository<LegoPart, String> {
    Page<LegoPart> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
