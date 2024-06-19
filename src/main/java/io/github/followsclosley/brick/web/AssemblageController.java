package io.github.followsclosley.brick.web;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssemblageController {

//    @Autowired
//    private VersionedConverter converter;
//
//    @Autowired
//    private AssemblageRepository repository;
//
//    @GetMapping(value = "/{version}/assemblage/{id}", produces = "application/json")
//    AssemblageDto getPiece(@PathVariable(name = "version") String version, @PathVariable String id) {
//        Optional<Assemblage> assemblage = repository.findById(id);
//        if (assemblage.isPresent()) {
//            AssemblageDto dto = converter.map(assemblage.get(), AssemblageDto.class, version);
//            return dto;
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find assemblage with id %s", id));
//        }
//    }
}
