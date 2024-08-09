package io.github.followsclosley.brick.data.repository;

import io.github.followsclosley.brick.data.LegoColor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface LegoColorRepository extends JpaRepository<LegoColor, String> {
    Page<LegoColor> findByNameLike(@Param("name") String name, Pageable pageable);
}
