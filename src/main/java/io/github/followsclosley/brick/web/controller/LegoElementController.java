package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.LegoElement;
import io.github.followsclosley.brick.data.repository.LegoElementRepository;
import io.github.followsclosley.brick.web.converter.VersionedMapperFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LegoElementController {
    private final LegoElementRepository legoElementRepository;
    private final VersionedMapperFactory<LegoElement> mapper;

//    @GetMapping(value = "/{version}/element", produces = "application/json")
//    Page<?> getColorsByName(@PathVariable(name = "version") String version, @Param("name") String name, Pageable pageable) {
//        Page<Element> page = elementRepository.findByNameContainingIgnoreCase(name, pageable);
//        List<?> colors = page.getContent().stream().map(c -> mapper.map(c, version)).toList();
//        return new PageImpl<>(colors, page.getPageable(), page.getTotalElements());
//    }

    @GetMapping(value = "/{version}/element/{id}", produces = "application/json")
    ResponseEntity<?> getColor(@PathVariable(name = "version") String version, @PathVariable String id) {
        return ResponseEntity.ok(mapper.map(legoElementRepository.getReferenceById(id), version));
    }
}
