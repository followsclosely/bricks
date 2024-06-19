package io.github.followsclosley.brick.web;

import io.github.followsclosley.brick.data.Franchise;
import io.github.followsclosley.brick.data.repository.ColorRepository;
import io.github.followsclosley.brick.data.repository.FranchiseRepository;
import io.github.followsclosley.brick.data.repository.NativeQueryRepository;
import io.github.followsclosley.brick.web.converter.VersionedMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequiredArgsConstructor
public class FranchiseController {
    private final ColorRepository colorRepository;
    private final ApplicationContext context;
    @Autowired
    private FranchiseRepository repository;
    @Autowired
    NativeQueryRepository nativeQueryRepository;

    private Map<String, VersionedMapper<Franchise, ?>> mappers;

    @PostConstruct
    public void init() {
        mappers = context.getBeansOfType(VersionedMapper.class).values()
                .stream()
                .collect(Collectors.toMap(VersionedMapper::getVersion, Function.identity()));
    }

    @GetMapping(value = "/{version}/franchise", produces = "application/json")
    Page<?> getFranchisesByName(@PathVariable(name = "version") String version, @Param("name") String name, Pageable pageable) {
        VersionedMapper<Franchise, ?> mapper = mappers.get(version);
        Page<Franchise> page = repository.findByNameContainingIgnoreCase(name, pageable);
        List<?> parts = page.getContent().stream().map(e -> mapper.map(e)).toList();
        return new PageImpl<>(parts, page.getPageable(), page.getTotalElements());
    }

    @GetMapping(value = "/{version}/query/{name}", produces = "application/json")
    List<Map<String,Object>> nativeQueryRepository(@PathVariable(name = "version") String version, @PathVariable("name") String name){
        return nativeQueryRepository.getSummary(name);
    }

    @GetMapping(value = "/{version}/franchise/{id}", produces = "application/json")
    ResponseEntity<?> getElement(@PathVariable(name = "version") String version, @PathVariable String id) {
        VersionedMapper<Franchise, ?> mapper = mappers.get(version);
        Franchise franchise = repository.getReferenceById(id);
        return  ResponseEntity.ok(mapper.map(franchise));
    }
}
