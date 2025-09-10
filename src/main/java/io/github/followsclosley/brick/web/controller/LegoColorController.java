package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.entity.LegoColor;
import io.github.followsclosley.brick.data.repository.LegoColorRepository;
import io.github.followsclosley.brick.web.dto.v1.LegoColorDto;
import io.github.followsclosley.brick.web.mapper.v1.LegoColorMapperV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LegoColorController {
    private final LegoColorRepository legoColorRepository;

    private final LegoColorMapperV1 colorMapper;

//    @GetMapping(value = "/color", produces = "application/json")
//    Page<LegoColor> getColorsByName(@Param("name") String name, Pageable pageable) {
//        return legoColorRepository.findByNameLike(name, pageable);
//    }

    @GetMapping(value = "/colors", produces = "application/json")
    List<LegoColorDto> getColors() {
        List<LegoColor> entities = legoColorRepository.findAll();
        return entities.stream().map(colorMapper::toDto).collect(Collectors.toCollection(ArrayList::new));
    }

    @GetMapping(value = "/color", produces = "application/json")
    List<LegoColorDto> getColorsByName(@Param("name") String name) {
        List<LegoColor> entities = legoColorRepository.findByNameLike(name);
        return entities.stream().map(colorMapper::toDto).collect(Collectors.toCollection(ArrayList::new));
    }

    @GetMapping(value = "/color/{id}", produces = "application/json")
    LegoColorDto getColor(@PathVariable String id) {
        return colorMapper.toDto(legoColorRepository.getReferenceById(id));
    }
}
