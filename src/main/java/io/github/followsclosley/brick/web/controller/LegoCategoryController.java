package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.repository.LegoCategoryRepository;
import io.github.followsclosley.brick.dto.v1.LegoCategoryDto;
import io.github.followsclosley.brick.mapper.v1.LegoCategoryMapperV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LegoCategoryController {

    private final LegoCategoryRepository repository;
    private final LegoCategoryMapperV1 legoCategoryMapper;

    @GetMapping(value = "/categories", produces = "application/json")
    List<LegoCategoryDto> getLegoThemes(@RequestParam(defaultValue = "False") Boolean children) {
        return repository.findAll().stream().map(legoCategoryMapper::toDto).collect(Collectors.toCollection(ArrayList::new));
    }

    @GetMapping(value = "/category/{id}", produces = "application/json")
    LegoCategoryDto getCategory(@PathVariable String id, @RequestParam Boolean nested) {
        return legoCategoryMapper.toDto(repository.getReferenceById(id));
    }

}
