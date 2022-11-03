package io.github.followsclosley.brick.web;

import io.github.followsclosley.brick.jpa.Element;
import io.github.followsclosley.brick.jpa.repository.ElementRepository;
import io.github.followsclosley.brick.web.converter.VersionedConverter;
import io.github.followsclosley.brick.web.dto.ElementDto;
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
    @Autowired private ElementRepository repository;
    @Autowired private VersionedConverter converter;

    @GetMapping(value = "/{version}/element", produces = "application/json")
    Page<ElementDto> getElementsByName(@PathVariable(name = "version") String version, @Param("name") String name, Pageable pageable) {
        Page<Element> page = repository.findByNameContainingIgnoreCase(name, pageable);
        List<ElementDto> parts = page.getContent().stream().map(e->converter.map(e, ElementDto.class, version)).toList();
        return new PageImpl<>(parts, page.getPageable(), page.getTotalElements());
    }

    @GetMapping(value = "/{version}/element/{id}", produces = "application/json")
    ElementDto getElement(@PathVariable(name = "version") String version, @PathVariable String id) {
        return converter.map(repository.getReferenceById(id), ElementDto.class, version);
    }
}
