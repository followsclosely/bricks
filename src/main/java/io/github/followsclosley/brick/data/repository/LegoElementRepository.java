package io.github.followsclosley.brick.data.repository;

import io.github.followsclosley.brick.data.entity.LegoElement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegoElementRepository extends JpaRepository<LegoElement, String> {
    //Page<Element> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
