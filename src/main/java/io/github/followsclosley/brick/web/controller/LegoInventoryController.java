package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.entity.LegoInventory;
import io.github.followsclosley.brick.data.repository.LegoInventoryRepository;
import io.github.followsclosley.brick.web.dto.v1.LegoInventoryDto;
import io.github.followsclosley.brick.web.mapper.v1.LegoInventoryMapperV1;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class LegoInventoryController {
    private final LegoInventoryRepository repository;
    private final LegoInventoryMapperV1 legoInventoryMapper;

    @GetMapping(value = "/set/{id}/inventory", produces = "application/json")
    LegoInventoryDto getLegoInventory(@PathVariable String id) {
        List<LegoInventory> inventories = repository.findByLegoSetId(id);
        return legoInventoryMapper.toDto(inventories.get(0));
    }
}
