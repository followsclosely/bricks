package io.github.followsclosley.brick.data.repository;

import io.github.followsclosley.brick.data.Category;
import io.github.followsclosley.brick.data.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SetRepository extends JpaRepository<Set, String> {
    Page<Set> findByNameLike(String name, Pageable pageable);
}