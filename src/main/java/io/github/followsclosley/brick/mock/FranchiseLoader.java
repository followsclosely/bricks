package io.github.followsclosley.brick.mock;

import io.github.followsclosley.brick.jpa.Address;
import io.github.followsclosley.brick.jpa.Franchise;
import io.github.followsclosley.brick.jpa.Wall;
import io.github.followsclosley.brick.jpa.repository.FranchiseRepository;
import io.github.followsclosley.brick.jpa.repository.PieceRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;

@Component
@ConditionalOnExpression("${catalog.load-on-startup:false}")
public class FranchiseLoader implements ApplicationRunner {

    final Logger logger = LoggerFactory.getLogger(FranchiseLoader.class);

    @Value("${catalog.resources.franchise}")
    private Resource resource;

    @Autowired
    private PieceRepository pieceRepository;

    @Autowired
    private FranchiseRepository repository;

    /**
     * A tab delimited file containing all categories.
     * Category ID	Category Name	Number	Name
     *
     * @throws IOException if anything goes wrong with reading the resource
     */
    public void run(ApplicationArguments args) throws Exception {

        int counter = 0;

        CSVFormat csvParser = CSVFormat.DEFAULT.builder()
                .setHeader().setSkipHeaderRecord(true)
                .setCommentMarker('#')
                .setDelimiter(',')
                .setIgnoreEmptyLines(true)
                .build();

        try (InputStream in = resource.getInputStream(); Reader reader = new InputStreamReader(in)) {
            for (CSVRecord record : csvParser.parse(reader)) {
                try {
                    Franchise franchise = new Franchise();
                    franchise.setName(record.get(0));
                    if (record.getRecordNumber() > 1) {
                        franchise.setAddress(Address.builder().district(record.get(1)).build());
                    }

                    Wall wall = Wall.builder().name("Default").build();
                    wall.setPieces(Arrays.asList(pieceRepository.getReferenceById("4211575"))
                    );
                    franchise.setWalls(Arrays.asList(wall));

                    repository.save(franchise);
                    counter++;
                } catch (Exception e) {
                    logger.error("Error loading franchise: {}", record, e);
                }
            }
        }

        logger.info("Inserted {} franchises.", counter);
    }

//    public void xrun(ApplicationArguments args) throws Exception {
//
//        int counter = 0;
//        Franchise tysons = new Franchise();
//        tysons.setId("tyson-corner-va");
//        tysons.setName("LEGO® Store Tysons Corner");
//        tysons.setAddress(Address.builder()
//                .district("Virginia")
//                .city("McLean")
//                .postalArea("22102")
//                .country("USA").build());
//
//        repository.save(tysons);
//
//        logger.info("Inserted {} pieces.", counter);
//    }
}
