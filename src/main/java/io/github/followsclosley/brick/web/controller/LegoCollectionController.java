package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.entity.LegoCollection;
import io.github.followsclosley.brick.data.repository.LegoCollectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LegoCollectionController {
    private final LegoCollectionRepository legoCollectionRepository;

    @GetMapping(value = "/collection/{id}", produces = "application/json")
    LegoCollection getColor(@PathVariable String id) {
        return legoCollectionRepository.getReferenceById(id);
    }
}
