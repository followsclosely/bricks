package io.github.followsclosley.brick.web;

import io.github.followsclosley.brick.data.Category;
import io.github.followsclosley.brick.data.Color;
import io.github.followsclosley.brick.data.repository.CategoryRepository;
import io.github.followsclosley.brick.web.converter.VersionedMapper;
import io.github.followsclosley.brick.web.converter.VersionedMapperFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepository repository;
    private final VersionedMapperFactory<Category> mapper;

    @GetMapping(value = "/{version}/category", produces = "application/json")
    Page<?> getCategoriesByName(@PathVariable(name = "version") String version, @Param("name") String name, Pageable pageable) {
        Page<Category> page = repository.query(name, pageable);
        List<?> colors = page.getContent().stream().map(c -> mapper.map(c, version)).toList();
        return new PageImpl<>(colors, page.getPageable(), page.getTotalElements());
    }

    @GetMapping(value = "/{version}/category/{id}", produces = "application/json")
    ResponseEntity<?> getCategory(@PathVariable(name = "version") String version, @PathVariable String id) {
        return ResponseEntity.ok(mapper.map(repository.getReferenceById(id), version));
    }
}
