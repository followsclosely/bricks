package io.github.followsclosley.brick.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElementRepository extends JpaRepository<Element, String> {
    Page<Element> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
