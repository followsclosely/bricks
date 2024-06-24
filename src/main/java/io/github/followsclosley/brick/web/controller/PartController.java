package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.Part;
import io.github.followsclosley.brick.data.repository.PartRepository;
import io.github.followsclosley.brick.web.converter.VersionedMapperFactory;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PartController {
    private final PartRepository repository;
    private final VersionedMapperFactory<Part> mapper;

//    @GetMapping(value = "/{version}/piece", produces = "application/json")
//    Page<?> getPiecesByName(@PathVariable(name = "version") String version, @Param("name") String name, Pageable pageable) {
//        Page<Piece> page = repository.findByElementNameContainingIgnoreCase(name, pageable);
//        List parts = page.getContent().stream().map(piece -> mapper.map(piece, version)).toList();
//        return new PageImpl<>(parts, page.getPageable(), page.getTotalElements());
//    }

    @GetMapping(value = "/{version}/piece/{id}", produces = "application/json")
    ResponseEntity<?> getPiece(@PathVariable(name = "version") String version, @PathVariable String id) {
        return ResponseEntity.ok(mapper.map(repository.getReferenceById(id), version));
    }
}
