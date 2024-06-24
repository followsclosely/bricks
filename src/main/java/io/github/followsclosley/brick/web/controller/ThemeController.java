package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.Theme;
import io.github.followsclosley.brick.data.repository.ThemeRepository;
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
public class ThemeController {
    private final ThemeRepository repository;
    private final VersionedMapperFactory<Theme> mapper;

    @GetMapping(value = "/{version}/theme", produces = "application/json")
    Page<?> getCategoriesByName(@PathVariable(name = "version") String version, @Param("name") String name, Pageable pageable) {
        Page<Theme> page = repository.findByNameLike(name, pageable);
        List<?> colors = page.getContent().stream().map(c -> mapper.map(c, version)).toList();
        return new PageImpl<>(colors, page.getPageable(), page.getTotalElements());
    }

    @GetMapping(value = "/{version}/theme/{id}", produces = "application/json")
    ResponseEntity<?> getCategory(@PathVariable(name = "version") String version, @PathVariable String id) {
        return ResponseEntity.ok(mapper.map(repository.getReferenceById(id), version));
    }
}
