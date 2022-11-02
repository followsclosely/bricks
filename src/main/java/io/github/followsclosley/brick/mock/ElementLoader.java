package io.github.followsclosley.brick.mock;

import io.github.followsclosley.brick.jpa.Category;
import io.github.followsclosley.brick.jpa.Element;
import io.github.followsclosley.brick.jpa.repository.CategoryRepository;
import io.github.followsclosley.brick.jpa.repository.ElementRepository;
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
import java.util.Map;
import java.util.stream.Collectors;

@Order(3)
@Component
@ConditionalOnExpression("${catalog.load-on-startup:false}")
public class ElementLoader implements ApplicationRunner {

    final Logger logger = LoggerFactory.getLogger(ElementLoader.class);

    @Value("${catalog.resources.bricklink.elements}")
    private Resource resource;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ElementRepository elementRepository;

    /**
     * A tab delimited file containing all categories.
     * Category ID	Category Name	Number	Name
     *
     * @throws IOException if anything goes wrong with reading the resource
     */
    public void run(ApplicationArguments args) throws Exception {

        Map<String, Category> categories = categoryRepository.findAll().stream().collect(Collectors.toMap(Category::getId, c -> c));

        int counter = 0;

        CSVFormat csvParser = CSVFormat.DEFAULT.builder()
                .setHeader().setSkipHeaderRecord(true)
                .setDelimiter('\t')
                .setIgnoreEmptyLines(true)
                .build();

        try (InputStream in = resource.getInputStream(); Reader reader = new InputStreamReader(in)) {
            for (CSVRecord record : csvParser.parse(reader)) {
                Element element = new Element();
                element.setId(record.get(2));
                element.setName(record.get(3));
                element.setCategory(categories.get(record.get(0)));

                elementRepository.save(element);
                counter++;
                if (counter % 10000 == 0) {
                    logger.info("Inserted {} elements so far...", counter);
                }
            }
        }

        logger.info("Inserted {} elements.", counter);
    }
}
