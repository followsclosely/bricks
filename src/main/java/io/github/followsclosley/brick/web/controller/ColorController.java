package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.Color;
import io.github.followsclosley.brick.data.repository.ColorRepository;
import io.github.followsclosley.brick.web.converter.VersionedMapperFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ColorController {
    private final ColorRepository colorRepository;
    private final VersionedMapperFactory<Color> mapper;

    @GetMapping(value = "/{version}/color", produces = "application/json")
    Page<?> getColorsByName(@PathVariable(name = "version") String version, @Param("name") String name, Pageable pageable) {
        Page<Color> page = colorRepository.findByNameLike(name, pageable);
        List<?> colors = page.getContent().stream().map(c -> mapper.map(c, version)).toList();
        return new PageImpl<>(colors, page.getPageable(), page.getTotalElements());
    }

    @GetMapping(value = "/{version}/color/{id}", produces = "application/json")
    ResponseEntity<?> getColor(@PathVariable(name = "version") String version, @PathVariable String id) {
        return ResponseEntity.ok(mapper.map(colorRepository.getReferenceById(id), version));
    }
}
