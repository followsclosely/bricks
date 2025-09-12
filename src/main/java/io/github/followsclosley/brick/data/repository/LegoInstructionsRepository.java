package io.github.followsclosley.brick.data.repository;

import io.github.followsclosley.brick.data.entity.LegoInstructions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegoInstructionsRepository extends JpaRepository<LegoInstructions, String> {
    Page<LegoInstructions> findByNameLike(String name, Pageable pageable);
}