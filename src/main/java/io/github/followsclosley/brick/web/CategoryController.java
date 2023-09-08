package io.github.followsclosley.brick.web;

import io.github.followsclosley.brick.data.Category;
import io.github.followsclosley.brick.data.repository.CategoryRepository;
import io.github.followsclosley.brick.web.converter.VersionedConverter;
import io.github.followsclosley.brick.web.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryRepository repository;
    @Autowired
    private VersionedConverter converter;

    @GetMapping(value = "/{version}/category", produces = "application/json")
    Page<CategoryDto> getCategoriesByName(@PathVariable(name = "version") String version, @Param("name") String name, Pageable pageable) {
        
        Page<Category> page = repository.query(name, pageable);
        List<CategoryDto> colors = page.getContent().stream().map(c -> converter.map(c, CategoryDto.class, version)).toList();
        return new PageImpl<>(colors, page.getPageable(), page.getTotalElements());
    }

    @GetMapping(value = "/{version}/category/{id}", produces = "application/json")
    CategoryDto getCategory(@PathVariable(name = "version") String version, @PathVariable String id) {
        return converter.map(repository.getReferenceById(id), CategoryDto.class, version);
    }
}
