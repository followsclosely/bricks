package io.github.followsclosley.brick.data.repository;

import io.github.followsclosley.brick.data.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, String> {
    @Query(value = "SELECT * FROM Category c WHERE c.name LIKE %:name%",
            countQuery = "SELECT count(*) FROM Category c WHERE c.name LIKE %:name%",
            nativeQuery = true)
    Page<Category> query(@Param("name") String name, Pageable pageable);
}
