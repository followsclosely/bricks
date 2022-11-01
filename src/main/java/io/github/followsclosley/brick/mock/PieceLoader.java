package io.github.followsclosley.brick.mock;

import io.github.followsclosley.brick.jpa.*;
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

@Order(4)
@Component
@ConditionalOnExpression("${bricklink.catalog.load-on-startup:false}")
public class PieceLoader implements ApplicationRunner {

    final Logger logger = LoggerFactory.getLogger(PieceLoader.class);

    @Value( "${bricklink.catalog.resources.piece}" )
    private Resource resource;

    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private ElementRepository elementRepository;

    @Autowired
    private PieceRepository pieceRepository;

    /**
     * A tab delimited file containing all categories.
     *
     * <p>
     * Item No	Color	Code
     * </p>
     *
     * @throws IOException if anything goes wrong with reading the resource
     */
    public void run(ApplicationArguments args) throws Exception {

        Map<String, Color> colorsByName = colorRepository.findAll().stream().collect(Collectors.toMap(Color::getName, c -> c));

        int counter = 0;

        CSVFormat csvParser = CSVFormat.DEFAULT.builder()
                .setHeader().setSkipHeaderRecord(true)
                .setDelimiter('\t')
                .setIgnoreEmptyLines(true)
                .build();

        try (InputStream in = resource.getInputStream(); Reader reader = new InputStreamReader(in)) {
            for (CSVRecord record : csvParser.parse(reader)) {

                try {
                    String id = record.get(2);

                    String elementId = record.get(0);
                    Element element = elementRepository.getReferenceById(elementId);

                    String colorName = record.get(1);
                    Color color = colorsByName.get(colorName);

                    Piece piece = new Piece(id, element, color);

                    pieceRepository.save(piece);

                    counter++;
                    if (counter % 10000 == 0) {
                        logger.info("Inserted {} pieces so far...", counter);
                    }
                } catch(Throwable t){
                    logger.error("Error persisting record: {}", record);
                }
            }
        }

        logger.info("Inserted {} pieces.", counter);
    }
}
