package io.github.followsclosley.brick.jpa.repository;

import io.github.followsclosley.brick.jpa.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ColorRepository extends JpaRepository<Color, String> {
    @Query(value = "SELECT * FROM Color c WHERE c.name LIKE %:name%",
            countQuery = "SELECT count(*) FROM Color c WHERE c.name LIKE %:name%",
            nativeQuery = true)
    Page<Color> query(@Param("name") String name, Pageable pageable);
}
