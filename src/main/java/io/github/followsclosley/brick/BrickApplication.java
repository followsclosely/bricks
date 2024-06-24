package io.github.followsclosley.brick;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:applicationContext.xml"})
public class BrickApplication {
    public static void main(String[] args) {
        SpringApplication.run(BrickApplication.class, args);
    }
}
