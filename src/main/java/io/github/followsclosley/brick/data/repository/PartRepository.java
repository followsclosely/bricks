package io.github.followsclosley.brick.data.repository;

import io.github.followsclosley.brick.data.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, String> {
    Page<Part> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
