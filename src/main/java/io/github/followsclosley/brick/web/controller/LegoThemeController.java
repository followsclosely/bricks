package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.entity.LegoTheme;
import io.github.followsclosley.brick.data.repository.LegoThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LegoThemeController {

    private final LegoThemeRepository repository;

//    @GetMapping(value = "/theme", produces = "application/json")
//    List<LegoTheme> getLegoThemeByParentId(@Param("parentId") String parentId) {
//        return repository.findByParentId(parentId);
//    }

    @GetMapping(value = "/theme", produces = "application/json")
    List<LegoTheme> getLegoThemes(@RequestParam(defaultValue = "False") Boolean children) {

        List<LegoTheme> themes = repository.findAll();

        if( children) {

            Map<String, LegoTheme> map = themes.stream()
                    .collect(Collectors.toMap(LegoTheme::getId, Function.identity()));

            for (Iterator<LegoTheme> iterator = themes.iterator(); iterator.hasNext(); ) {
                LegoTheme theme = iterator.next();
                if (theme.getParent() != null) {
                    LegoTheme parent = map.get(theme.getParent().getId());
                    if (parent != null) {
                        parent.getChildren().add(theme);
                        iterator.remove();
                    }
                }
            }
        }

        return themes;
    }

    @GetMapping(value = "/theme/{id}", produces = "application/json")
    LegoTheme getCategory(@PathVariable String id, @RequestParam Boolean nested) {
        return repository.getReferenceById(id);
    }

}
