package io.github.followsclosley.brick.web;

import io.github.followsclosley.brick.jpa.Color;
import io.github.followsclosley.brick.jpa.repository.ColorRepository;
import io.github.followsclosley.brick.web.converter.VersionedConverter;
import io.github.followsclosley.brick.web.dto.ColorDto;
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
public class ColorController {
    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private VersionedConverter converter;

    @GetMapping(value = "/{version}/color", produces = "application/json")
    Page<ColorDto> getColorsByName(@PathVariable(name = "version") String version, @Param("name") String name, Pageable pageable) {
        Page<Color> page = colorRepository.query(name, pageable);
        List<ColorDto> colors = page.getContent().stream().map(c -> converter.map(c, ColorDto.class, version)).toList();
        return new PageImpl<>(colors, page.getPageable(), page.getTotalElements());
    }

    @GetMapping(value = "/{version}/color/{id}", produces = "application/json")
    ColorDto getColor(@PathVariable(name = "version") String version, @PathVariable String id) {
        return converter.map(colorRepository.getReferenceById(id), ColorDto.class, version);
    }

}
