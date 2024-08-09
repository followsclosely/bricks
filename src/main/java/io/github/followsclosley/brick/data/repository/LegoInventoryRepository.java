package io.github.followsclosley.brick.data.repository;

import io.github.followsclosley.brick.data.LegoInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LegoInventoryRepository extends JpaRepository<LegoInventory, String> {
    List<LegoInventory> findByLegoSetId(String legoSetId);
}