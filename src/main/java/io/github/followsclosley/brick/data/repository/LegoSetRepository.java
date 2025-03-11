package io.github.followsclosley.brick.data.repository;

import io.github.followsclosley.brick.data.entity.LegoSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegoSetRepository extends JpaRepository<LegoSet, String> {
    Page<LegoSet> findByNameLike(String name, Pageable pageable);
}