package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.repository.LegoMinifigRepository;
import io.github.followsclosley.brick.dto.v1.LegoMinifigDto;
import io.github.followsclosley.brick.mapper.v1.LegoMinifigMapperV1;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LegoMinifigController {
    private final LegoMinifigRepository repository;
    private final LegoMinifigMapperV1 legoMinifigMapper;

    @GetMapping(value = "/minifig/{id}", produces = "application/json")
    LegoMinifigDto getLegoSetById(@PathVariable String id) {
        return legoMinifigMapper.toDto(repository.getReferenceById(id));
    }
}
