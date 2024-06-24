package io.github.followsclosley.brick.data.repository;

import io.github.followsclosley.brick.data.Minifig;
import io.github.followsclosley.brick.data.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MinifigRepository extends JpaRepository<Minifig, String> {
    Page<Set> findByNameLike(String name, Pageable pageable);
}