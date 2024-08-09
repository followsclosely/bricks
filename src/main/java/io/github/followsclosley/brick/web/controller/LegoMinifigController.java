package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.LegoMinifig;
import io.github.followsclosley.brick.data.repository.LegoMinifigRepository;
import io.github.followsclosley.brick.web.converter.VersionedMapperFactory;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LegoMinifigController {
    private final LegoMinifigRepository repository;
    private final VersionedMapperFactory<LegoMinifig> mapper;

    @GetMapping(value = "/{version}/minifig/{id}", produces = "application/json")
    ResponseEntity<?> getPiece(@PathVariable(name = "version") String version, @PathVariable String id) {
        return ResponseEntity.ok(mapper.map(repository.getReferenceById(id), version));
    }
}
