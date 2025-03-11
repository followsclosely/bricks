package io.github.followsclosley.brick.data.repository;

import io.github.followsclosley.brick.data.entity.change.ChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeLogRepository extends JpaRepository<ChangeLog, String> {
}
