package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.LegoInventory;
import io.github.followsclosley.brick.data.repository.LegoInventoryRepository;
import io.github.followsclosley.brick.web.converter.VersionedMapperFactory;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class LegoInventoryController {
    private final LegoInventoryRepository repository;
    private final VersionedMapperFactory<LegoInventory> mapper;

    @GetMapping(value = "/{version}/set/{id}/inventory", produces = "application/json")
    ResponseEntity<?> getLegoInventory(@PathVariable String version, @PathVariable String id) {
        List<LegoInventory> inventories = repository.findByLegoSetId(id);
        return ResponseEntity.ok(mapper.map(inventories.get(0), version));
    }
}
