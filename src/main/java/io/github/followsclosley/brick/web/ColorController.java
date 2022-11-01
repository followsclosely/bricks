package io.github.followsclosley.brick.web;

import io.github.followsclosley.brick.jpa.Color;
import io.github.followsclosley.brick.jpa.ColorRepository;
import io.github.followsclosley.brick.web.converter.ColorConverter;
import io.github.followsclosley.brick.web.pojo.ColorDto;
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
public class ColorController {
    @Autowired private ColorRepository colorRepository;
    @Autowired private ColorConverter colorConverter;

    @GetMapping(value = "color", produces = "application/json")
    Page<ColorDto> getColorsByName(@Param("name") String name, Pageable pageable) {
        Page<Color> page = colorRepository.query(name, pageable);
        List<ColorDto> colors = page.getContent().stream().map(colorConverter::convert).toList();
        return new PageImpl<>(colors, page.getPageable(), page.getTotalPages());
    }

    @GetMapping(value = "color/{id}", produces = "application/json")
    ColorDto getColor(@PathVariable String id) {
        return colorConverter.convert(colorRepository.getReferenceById(id));
    }
}
