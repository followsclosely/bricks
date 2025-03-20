package io.github.followsclosley.brick.web.controller;

import io.github.followsclosley.brick.data.entity.LegoCollection;
import io.github.followsclosley.brick.data.repository.LegoCollectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LegoCollectionGraph{

    private final LegoCollectionRepository repository;

    @QueryMapping
    public LegoCollection legoCollection(@Argument String id) {
        LegoCollection collection = repository.findById(id).orElseThrow();
        return collection;
    }

    @QueryMapping
    public List<LegoCollection> legoCollectionList(@Argument Integer page) {
        return repository.findAll();
    }

}