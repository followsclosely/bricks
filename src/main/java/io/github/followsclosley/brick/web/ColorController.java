package io.github.followsclosley.brick.web;

import io.github.followsclosley.brick.data.Color;
import io.github.followsclosley.brick.data.repository.ColorRepository;
import io.github.followsclosley.brick.web.converter.VersionedMapper;
import jakarta.annotation.PostConstruct;
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
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ColorController {
    private final ColorRepository colorRepository;
    private final ApplicationContext context;
    private Map<String, VersionedMapper<Color, ?>> mappers;

    @PostConstruct
    public void init() {
        mappers = context.getBeansOfType(VersionedMapper.class).values()
                .stream()
                .collect(Collectors.toMap(VersionedMapper::getVersion, Function.identity()));
    }

    @GetMapping(value = "/{version}/color", produces = "application/json")
    Page<?> getColorsByName(@PathVariable(name = "version") String version, @Param("name") String name, Pageable pageable) {
        VersionedMapper<Color, ?> mapper = mappers.get(version);
        Page<Color> page = colorRepository.findAll(pageable);
        List<?> colors = page.getContent().stream().map(c -> mapper.map(c)).toList();
        return new PageImpl<>(colors, page.getPageable(), page.getTotalElements());
    }

    @GetMapping(value = "/{version}/color/{id}", produces = "application/json")
    ResponseEntity<?> getColor(@PathVariable(name = "version") String version, @PathVariable String id) {
        VersionedMapper<Color, ?> mapper = mappers.get(version);
        return ResponseEntity.ok(mapper.map(colorRepository.getReferenceById(id)));
    }

}
