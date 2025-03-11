package io.github.followsclosley.brick.data.repository;

import io.github.followsclosley.brick.data.LegoColor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LegoColorRepository extends JpaRepository<LegoColor, String> {
    List<LegoColor> findByNameLike(@Param("name") String name);
    Page<LegoColor> findByNameLike(@Param("name") String name, Pageable pageable);
}
