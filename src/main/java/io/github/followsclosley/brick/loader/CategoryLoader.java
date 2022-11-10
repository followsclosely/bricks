package io.github.followsclosley.brick.loader;

import io.github.followsclosley.brick.jpa.Category;
import io.github.followsclosley.brick.jpa.repository.CategoryRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

@Order(2)
@Component
@ConditionalOnExpression("${catalog.load-on-startup:false}")
public class CategoryLoader implements ApplicationRunner {

    final Logger logger = LoggerFactory.getLogger(CategoryLoader.class);

    @Value("${catalog.resources.bricklink.categories}")
    private Resource resource;

    @Autowired
    private CategoryRepository repository;

    /**
     * A tab delimited file containing all categories.
     * Category ID	Category Name
     *
     * @throws IOException if anything goes wrong with reading the resource
     */
    public void run(ApplicationArguments args) throws Exception {

        int counter = 0;

        CSVFormat csvParser = CSVFormat.DEFAULT.builder()
                .setHeader().setSkipHeaderRecord(true)
                .setDelimiter('\t')
                .setIgnoreEmptyLines(true)
                .build();

        try (InputStream in = resource.getInputStream(); Reader reader = new InputStreamReader(in)) {
            for (CSVRecord record : csvParser.parse(reader)) {
                Category category = new Category(record.get(0), record.get(1));
                repository.save(category);
                counter++;
            }
        }

        logger.info("Inserted {} categories.", counter);
    }
}
