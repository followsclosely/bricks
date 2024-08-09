package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.LegoTheme;
import io.github.followsclosley.brick.data.repository.LegoThemeRepository;
import io.github.followsclosley.brick.web.converter.VersionedMapperFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LegoThemeController {
    private final LegoThemeRepository repository;
    private final VersionedMapperFactory<LegoTheme> mapper;

    @GetMapping(value = "/{version}/theme", produces = "application/json")
    List<?> getCategoriesByParentId(@PathVariable(name = "version") String version, @Param("parentId") String parentId) {
        List<LegoTheme> page = repository.findByParentId(parentId);
        return page.stream().map(c -> mapper.map(c, version)).toList();
    }

    @GetMapping(value = "/{version}/theme/nested", produces = "application/json")
    List<?> getCategoriesByParentId(@PathVariable(name = "version") String version) {
        List<LegoTheme> page = repository.findAll();
        return page.stream().map(c -> mapper.map(c, version)).toList();
    }

    @GetMapping(value = "/{version}/theme/{id}", produces = "application/json")
    ResponseEntity<?> getCategory(@PathVariable(name = "version") String version, @PathVariable String id) {
        return ResponseEntity.ok(mapper.map(repository.getReferenceById(id), version));
    }
}
