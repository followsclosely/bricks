package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.LegoMinifig;
import io.github.followsclosley.brick.data.repository.LegoMinifigRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LegoMinifigController {

    private final LegoMinifigRepository repository;

    @GetMapping(value = "/minifig/{id}", produces = "application/json")
    LegoMinifig getLegoMinifigById(@PathVariable String id) {
        return repository.getReferenceById(id);
    }
}
