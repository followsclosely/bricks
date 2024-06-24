package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.Part;
import io.github.followsclosley.brick.data.Set;
import io.github.followsclosley.brick.data.repository.PartRepository;
import io.github.followsclosley.brick.data.repository.SetRepository;
import io.github.followsclosley.brick.web.converter.VersionedMapperFactory;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SetController {
    private final SetRepository repository;
    private final VersionedMapperFactory<Set> mapper;

    @GetMapping(value = "/{version}/set/{id}", produces = "application/json")
    ResponseEntity<?> getPiece(@PathVariable(name = "version") String version, @PathVariable String id) {
        return ResponseEntity.ok(mapper.map(repository.getReferenceById(id), version));
    }
}
