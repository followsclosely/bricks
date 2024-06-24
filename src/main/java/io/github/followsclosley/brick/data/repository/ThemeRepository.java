package io.github.followsclosley.brick.data.repository;

import io.github.followsclosley.brick.data.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ThemeRepository extends JpaRepository<Theme, String> {
    Page<Theme> findByNameLike(String name, Pageable pageable);
}
