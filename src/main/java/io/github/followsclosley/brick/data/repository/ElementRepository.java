package io.github.followsclosley.brick.data.repository;

import io.github.followsclosley.brick.data.Element;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElementRepository extends JpaRepository<Element, String> {
    Page<Element> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
