package io.github.followsclosley.brick.web;

import io.github.followsclosley.brick.data.Assemblage;
import io.github.followsclosley.brick.data.repository.AssemblageRepository;
import io.github.followsclosley.brick.web.converter.VersionedConverter;
import io.github.followsclosley.brick.web.dto.AssemblageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class AssemblageController {

    @Autowired
    private VersionedConverter converter;

    @Autowired
    private AssemblageRepository repository;

    @GetMapping(value = "/{version}/assemblage/{id}", produces = "application/json")
    AssemblageDto getPiece(@PathVariable(name = "version") String version, @PathVariable String id) {
        Optional<Assemblage> assemblage = repository.findById(id);
        if (assemblage.isPresent()) {
            AssemblageDto dto = converter.map(assemblage.get(), AssemblageDto.class, version);
            return dto;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find assemblage with id %s", id));
        }
    }
}
