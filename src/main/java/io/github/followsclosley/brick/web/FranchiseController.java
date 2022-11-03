package io.github.followsclosley.brick.web;

import io.github.followsclosley.brick.jpa.Franchise;
import io.github.followsclosley.brick.jpa.repository.FranchiseRepository;
import io.github.followsclosley.brick.web.converter.VersionedConverter;
import io.github.followsclosley.brick.web.dto.FranchiseDto;
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
public class FranchiseController {
    @Autowired private FranchiseRepository repository;
    @Autowired private VersionedConverter converter;

    @GetMapping(value = "/{version}/franchise", produces = "application/json")
    Page<FranchiseDto> getFranchisesByName(@PathVariable(name = "version") String version, @Param("name") String name, Pageable pageable) {
        Page<Franchise> page = repository.findByNameContainingIgnoreCase (name, pageable);
        List<FranchiseDto> parts = page.getContent().stream().map(e->converter.map(e, FranchiseDto.class, version)).toList();
        return new PageImpl<>(parts, page.getPageable(), page.getTotalElements());
    }

    @GetMapping(value = "/{version}/franchise/{id}", produces = "application/json")
    FranchiseDto getElement(@PathVariable(name = "version") String version, @PathVariable String id) {
        Franchise franchise = repository.getReferenceById(id);
        return converter.map(franchise, FranchiseDto.class, version);
    }
}
