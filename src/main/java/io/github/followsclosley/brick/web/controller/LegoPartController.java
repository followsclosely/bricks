package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.entity.LegoPart;
import io.github.followsclosley.brick.data.repository.LegoPartRepository;
import io.github.followsclosley.brick.dto.v1.LegoPartDto;
import io.github.followsclosley.brick.mapper.v1.LegoPartMapperV1;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LegoPartController {
    private final LegoPartRepository repository;
    private final LegoPartMapperV1 legoPartMapper;

//    @GetMapping(value = "/piece", produces = "application/json")
//    Page<?> getPiecesByName( @Param("name") String name, Pageable pageable) {
//        Page<Piece> page = repository.findByElementNameContainingIgnoreCase(name, pageable);
//        List parts = page.getContent().stream().map(piece -> mapper.map(piece).toList();
//        return new PageImpl<>(parts, page.getPageable(), page.getTotalElements());
//    }

    @GetMapping(value = "/piece/{id}", produces = "application/json")
    LegoPartDto getLegoPartById(@PathVariable String id) {
        LegoPart entity = repository.getReferenceById(id);
        return legoPartMapper.toDto(entity);
    }
}
