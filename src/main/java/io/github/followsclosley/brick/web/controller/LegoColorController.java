package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.entity.LegoColor;
import io.github.followsclosley.brick.data.repository.LegoColorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LegoColorController {
    private final LegoColorRepository legoColorRepository;


//    @GetMapping(value = "/color", produces = "application/json")
//    Page<LegoColor> getColorsByName(@Param("name") String name, Pageable pageable) {
//        return legoColorRepository.findByNameLike(name, pageable);
//    }

    @GetMapping(value = "/colors", produces = "application/json")
    List<LegoColor> getColors() {
        return legoColorRepository.findAll();
    }

    @GetMapping(value = "/color", produces = "application/json")
    List<LegoColor> getColorsByName(@Param("name") String name) {
        return legoColorRepository.findByNameLike(name);
    }

    @GetMapping(value = "/color/{id}", produces = "application/json")
    LegoColor getColor(@PathVariable String id) {
        return legoColorRepository.getReferenceById(id);
    }
}
