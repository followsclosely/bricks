package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.entity.LegoSet;
import io.github.followsclosley.brick.data.repository.LegoSetRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LegoSetController {
    private final LegoSetRepository repository;

    @GetMapping(value = "/set/{id}", produces = "application/json")
    LegoSet getLegoSetById(@PathVariable String id) {
        return repository.getReferenceById(id);
    }
}
