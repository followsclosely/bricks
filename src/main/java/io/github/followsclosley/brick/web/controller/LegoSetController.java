package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.repository.LegoSetRepository;
import io.github.followsclosley.brick.dto.v1.LegoSetDto;
import io.github.followsclosley.brick.mapper.v1.LegoSetMapperV1;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LegoSetController {
    private final LegoSetRepository repository;
    private final LegoSetMapperV1 legoSetMapper;

    @GetMapping(value = "/set/{id}", produces = "application/json")
    LegoSetDto getLegoSetById(@PathVariable String id) {
        return legoSetMapper.toDto(repository.getReferenceById(id));
    }
}
