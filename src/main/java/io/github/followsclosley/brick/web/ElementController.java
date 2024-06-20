package io.github.followsclosley.brick.web;

import io.github.followsclosley.brick.data.Color;
import io.github.followsclosley.brick.data.Element;
import io.github.followsclosley.brick.data.repository.ColorRepository;
import io.github.followsclosley.brick.data.repository.ElementRepository;
import io.github.followsclosley.brick.web.converter.VersionedMapperFactory;
import io.github.followsclosley.brick.web.converter.v1.ElementMapperV1;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ElementController {
    private final ElementRepository elementRepository;
    private final VersionedMapperFactory<Element> mapper;

    @GetMapping(value = "/{version}/element", produces = "application/json")
    Page<?> getColorsByName(@PathVariable(name = "version") String version, @Param("name") String name, Pageable pageable) {
        Page<Element> page = elementRepository.findByNameContainingIgnoreCase(name, pageable);
        List<?> colors = page.getContent().stream().map(c -> mapper.map(c, version)).toList();
        return new PageImpl<>(colors, page.getPageable(), page.getTotalElements());
    }

    @GetMapping(value = "/{version}/element/{id}", produces = "application/json")
    ResponseEntity<?> getColor(@PathVariable(name = "version") String version, @PathVariable String id) {
        return ResponseEntity.ok(mapper.map(elementRepository.getReferenceById(id), version));
    }
}
