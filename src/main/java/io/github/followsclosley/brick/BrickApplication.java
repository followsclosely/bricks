package io.github.followsclosley.brick;


import org.apache.commons.csv.CSVFormat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.EnableIntegration;

@EnableIntegration
@SpringBootApplication
public class BrickApplication {
    public static void main(String[] args) {
        SpringApplication.run
                (BrickApplication.class, args);
    }

    @Bean
    public CSVFormat csvParser() {
        return CSVFormat.DEFAULT.builder()
                .setHeader().setSkipHeaderRecord(true)
                .setDelimiter("\t")
                .setIgnoreEmptyLines(true)
                .build();
    }
}
