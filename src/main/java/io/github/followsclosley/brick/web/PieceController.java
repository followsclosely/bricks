package io.github.followsclosley.brick.web;

import io.github.followsclosley.brick.data.Piece;
import io.github.followsclosley.brick.data.repository.PieceRepository;
import io.github.followsclosley.brick.web.converter.VersionedMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class PieceController {
    private final PieceRepository repository;
    private final ApplicationContext context;
    private Map<String, VersionedMapper<Piece, ?>> mappers;

    @PostConstruct
    public void init() {
        mappers = context.getBeansOfType(VersionedMapper.class).values()
                .stream()
                .collect(Collectors.toMap(VersionedMapper::getVersion, Function.identity()));
    }

    @GetMapping(value = "/{version}/piece", produces = "application/json")
    Page<?> getPiecesByName(@PathVariable(name = "version") String version, @Param("name") String name, Pageable pageable) {
        VersionedMapper<Piece, ?> mapper = mappers.get(version);
        Page<Piece> page = repository.findByElementNameContainingIgnoreCase(name, pageable);
        List parts = page.getContent().stream().map(e -> mapper.map(e)).toList();
        return new PageImpl<>(parts, page.getPageable(), page.getTotalElements());
    }

    @GetMapping(value = "/{version}/piece/{id}", produces = "application/json")
    ResponseEntity<?> getPiece(@PathVariable(name = "version") String version, @PathVariable String id) {
        VersionedMapper<Piece, ?> mapper = mappers.get(version);
        return ResponseEntity.ok(mapper.map(repository.getReferenceById(id)));
    }
}
