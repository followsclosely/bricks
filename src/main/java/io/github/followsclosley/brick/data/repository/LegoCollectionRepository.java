package io.github.followsclosley.brick.data.repository;

import io.github.followsclosley.brick.data.entity.LegoCollection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegoCollectionRepository extends JpaRepository<LegoCollection, String> {
}
