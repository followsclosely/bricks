package io.github.followsclosley.brick.data.repository;

import io.github.followsclosley.brick.data.entity.LegoMinifig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegoMinifigRepository extends JpaRepository<LegoMinifig, String> {
    Page<LegoMinifig> findByNameLike(String name, Pageable pageable);
}