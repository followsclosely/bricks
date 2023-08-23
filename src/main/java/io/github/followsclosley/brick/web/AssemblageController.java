package io.github.followsclosley.brick.web;

import io.github.followsclosley.brick.jpa.Assemblage;
import io.github.followsclosley.brick.jpa.repository.AssemblageRepository;
import io.github.followsclosley.brick.web.converter.VersionedConverter;
import io.github.followsclosley.brick.web.dto.AssemblageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssemblageController {

    @Autowired
    private AssemblageRepository repository;

    @Autowired
    private VersionedConverter converter;

    @GetMapping(value = "/{version}/assemblage/{id}", produces = "application/json")
    AssemblageDto getPiece(@PathVariable(name = "version") String version, @PathVariable String id) {
        Assemblage assemblage = repository.getReferenceById(id);
        AssemblageDto dto = converter.map(assemblage, AssemblageDto.class, version);
        return dto;
    }
}
