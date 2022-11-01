package io.github.followsclosley.brick.web;

import io.github.followsclosley.brick.jpa.Category;
import io.github.followsclosley.brick.jpa.CategoryRepository;
import io.github.followsclosley.brick.web.converter.CategoryConverter;
import io.github.followsclosley.brick.web.pojo.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {
    @Autowired private CategoryRepository repository;
    @Autowired private CategoryConverter converter;

    @GetMapping(value = "category", produces = "application/json")
    Page<CategoryDto> getCategoriesByName(@Param("name") String name, Pageable pageable) {
        Page<Category> page = repository.query(name, pageable);
        List<CategoryDto> colors = page.getContent().stream().map(converter::convert).toList();
        return new PageImpl<>(colors, page.getPageable(), page.getTotalPages());
    }

    @GetMapping(value = "category/{id}", produces = "application/json")
    CategoryDto getCategory(@PathVariable String id) {
        return converter.convert(repository.getReferenceById(id));
    }
}
