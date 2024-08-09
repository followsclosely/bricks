package io.github.followsclosley.brick.data.repository;

import io.github.followsclosley.brick.data.LegoTheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LegoThemeRepository extends JpaRepository<LegoTheme, String> {
    List<LegoTheme> findByParentId(String parentId);
}
