package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.repository.LegoInstructionsRepository;
import io.github.followsclosley.brick.dto.v1.LegoInstructionsDto;
import io.github.followsclosley.brick.mapper.v1.LegoInstructionsMapperV1;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LegoInstructionsController {
    private final LegoInstructionsRepository repository;
    private final LegoInstructionsMapperV1 mapper;

    @GetMapping(value = "/instructions/{id}", produces = "application/json")
    LegoInstructionsDto getLegoSetById(@PathVariable String id) {
        return mapper.toDto(repository.getReferenceById(id));
    }
}
