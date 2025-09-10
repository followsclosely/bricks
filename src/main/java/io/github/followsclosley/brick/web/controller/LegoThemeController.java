package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.entity.LegoTheme;
import io.github.followsclosley.brick.data.repository.LegoThemeRepository;
import io.github.followsclosley.brick.web.dto.v1.LegoThemeDto;
import io.github.followsclosley.brick.web.mapper.v1.LegoThemeMapperV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LegoThemeController {

    private final LegoThemeRepository repository;
    private final LegoThemeMapperV1 legoThemeMapper;

//    @GetMapping(value = "/theme", produces = "application/json")
//    List<LegoTheme> getLegoThemeByParentId(@Param("parentId") String parentId) {
//        return repository.findByParentId(parentId);
//    }

    @GetMapping(value = "/theme", produces = "application/json")
    List<LegoThemeDto> getLegoThemes(@RequestParam(defaultValue = "False") Boolean children) {

        List<LegoTheme> entities = repository.findAll();
        List<LegoThemeDto> dtos = entities.stream().map(legoThemeMapper::toDto).collect(Collectors.toCollection(ArrayList::new));

        //Nest the child in the parent, i.e. make it hierarchical
        if (children) {
            Map<String, LegoThemeDto> map = dtos.stream()
                    .collect(Collectors.toMap(LegoThemeDto::getId, Function.identity()));

            for (Iterator<LegoThemeDto> iterator = dtos.iterator(); iterator.hasNext(); ) {
                LegoThemeDto theme = iterator.next();
                if (theme.getParent() != null) {
                    LegoThemeDto parent = map.get(theme.getParent().getId());
                    if (parent != null) {
                        parent.getChildren().add(theme);
                        iterator.remove();
                    }
                }
            }
        }

        return dtos;
    }

    @GetMapping(value = "/theme/{id}", produces = "application/json")
    LegoThemeDto getCategory(@PathVariable String id, @RequestParam Boolean nested) {
        return legoThemeMapper.toDto(repository.getReferenceById(id));
    }

}
