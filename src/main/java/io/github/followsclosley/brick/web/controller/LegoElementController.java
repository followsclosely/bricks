package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.entity.LegoElement;
import io.github.followsclosley.brick.data.repository.LegoElementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LegoElementController {
    private final LegoElementRepository legoElementRepository;

//    @GetMapping(value = "/element", produces = "application/json")
//    Page<?> getColorsByName( @Param("name") String name, Pageable pageable) {
//        Page<Element> page = elementRepository.findByNameContainingIgnoreCase(name, pageable);
//        List<?> colors = page.getContent().stream().map(c -> mapper.map(c).toList();
//        return new PageImpl<>(colors, page.getPageable(), page.getTotalElements());
//    }

    @GetMapping(value = "/element/{id}", produces = "application/json")
    LegoElement getLegoElementById(@PathVariable String id) {
        return legoElementRepository.getReferenceById(id);
    }
}
