package io.github.followsclosley.brick.web;

import io.github.followsclosley.brick.jpa.Element;
import io.github.followsclosley.brick.jpa.ElementRepository;
import io.github.followsclosley.brick.web.converter.ElementConverter;
import io.github.followsclosley.brick.web.pojo.ElementDto;
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
public class ElementController {
    @Autowired private ElementRepository repository;
    @Autowired private ElementConverter converter;

    @GetMapping(value = "element", produces = "application/json")
    Page<ElementDto> getPartsByName(@Param("name") String name, Pageable pageable) {
        Page<Element> page = repository.findByNameContainingIgnoreCase(name, pageable);
        List<ElementDto> parts = page.getContent().stream().map(converter::convert).toList();
        return new PageImpl<>(parts, page.getPageable(), page.getTotalPages());
    }

    @GetMapping(value = "element/{id}", produces = "application/json")
    ElementDto getPart(@PathVariable String id) {
        return converter.convert(repository.getReferenceById(id));
    }
}
