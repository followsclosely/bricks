package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.LegoInventory;
import io.github.followsclosley.brick.data.repository.LegoInventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class LegoInventoryController {
    private final LegoInventoryRepository repository;

    @GetMapping(value = "/set/{id}/inventory", produces = "application/json")
    LegoInventory getLegoInventory(@PathVariable String id) {
        List<LegoInventory> inventories = repository.findByLegoSetId(id);
        return inventories.get(0);
    }
}
