package io.github.followsclosley.brick.web;

import io.github.followsclosley.brick.data.Element;
import io.github.followsclosley.brick.data.repository.ElementRepository;
import io.github.followsclosley.brick.web.converter.ElementMapperV1;
import io.github.followsclosley.brick.web.dto.v1.ElementDtoV1;
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
public class ElementController {
    @Autowired
    private ElementRepository repository;
    @Autowired
    private ElementMapperV1 converter;

    @GetMapping(value = "/{version}/element", produces = "application/json")
    Page<ElementDtoV1> getElementsByName(@PathVariable(name = "version") String version, @Param("name") String name, Pageable pageable) {
        Page<Element> page = repository.findByNameContainingIgnoreCase(name, pageable);
        List<ElementDtoV1> parts = page.getContent().stream().map(e -> converter.toElementDto(e)).toList();
        return new PageImpl<>(parts, page.getPageable(), page.getTotalElements());
    }

    @GetMapping(value = "/{version}/element/{id}", produces = "application/json")
    ElementDtoV1 getElement(@PathVariable(name = "version") String version, @PathVariable String id) {
        return converter.toElementDto(repository.getReferenceById(id));
    }
}
