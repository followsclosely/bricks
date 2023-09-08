package io.github.followsclosley.brick.web;

import io.github.followsclosley.brick.data.Piece;
import io.github.followsclosley.brick.data.repository.PieceRepository;
import io.github.followsclosley.brick.web.converter.VersionedConverter;
import io.github.followsclosley.brick.web.dto.PieceDto;
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
public class PieceController {
    @Autowired
    private PieceRepository repository;
    @Autowired
    private VersionedConverter converter;

    @GetMapping(value = "/{version}/piece", produces = "application/json")
    Page<PieceDto> getPiecesByName(@PathVariable(name = "version") String version, @Param("name") String name, Pageable pageable) {
        Page<Piece> page = repository.findByElementNameContainingIgnoreCase(name, pageable);
        List<PieceDto> parts = page.getContent().stream().map(e -> converter.map(e, PieceDto.class, version)).toList();
        return new PageImpl<>(parts, page.getPageable(), page.getTotalElements());
    }

    @GetMapping(value = "/{version}/piece/{id}", produces = "application/json")
    PieceDto getPiece(@PathVariable(name = "version") String version, @PathVariable String id) {
        return converter.map(repository.getReferenceById(id), PieceDto.class, version);
    }
}
