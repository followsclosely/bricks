package io.github.followsclosley.brick;


import org.apache.commons.csv.CSVFormat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BrickApplication {
    public static void main(String[] args) {
        SpringApplication.run(BrickApplication.class, args);
    }

    @Bean
    public CSVFormat cvsParser(){
         return CSVFormat.DEFAULT.builder()
                .setHeader().setSkipHeaderRecord(true)
                .setDelimiter(',')
                .setIgnoreEmptyLines(true)
                .build();
    }
}
