package io.github.followsclosley.brick.mock;

import io.github.followsclosley.brick.jpa.Color;
import io.github.followsclosley.brick.jpa.repository.ColorRepository;
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

@Order(1)
@Component
@ConditionalOnExpression("${catalog.load-on-startup:false}")
public class ColorLoader implements ApplicationRunner {

    final Logger logger = LoggerFactory.getLogger(ColorLoader.class);

    @Value("${catalog.resources.bricklink.colors}")
    private Resource resource;

    @Autowired
    private ColorRepository repository;

    /**
     * A tab delimited file containing all colors.
     * Color ID | Color Name | RGB | Type | Parts | In Sets | Wanted | For Sale | Year From | Year To
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
                Color color = new Color();
                color.setId(record.get(0));
                color.setName(record.get(1));
                color.setRgb(record.get(2));
                color.setParts(record.get(3));
                repository.save(color);
                counter++;
            }
        }

        logger.info("Inserted {} colors.", counter);
    }
}
