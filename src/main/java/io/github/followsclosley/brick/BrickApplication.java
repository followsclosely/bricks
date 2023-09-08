package io.github.followsclosley.brick;

import com.google.common.collect.Lists;
import io.github.followsclosley.brick.data.Assemblage;
import io.github.followsclosley.brick.data.repository.AssemblageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@SpringBootApplication
public class BrickApplication {
    public static void main(String[] args) {
        SpringApplication.run(BrickApplication.class, args);
    }

    @Autowired
    private AssemblageRepository assemblageRepository;

    @ShellMethod("Loads all books")
    public String findAllBooks() {
        Iterable<Assemblage> collections = this.assemblageRepository.findAll();
        return Lists.newArrayList(collections).toString();
    }

}
