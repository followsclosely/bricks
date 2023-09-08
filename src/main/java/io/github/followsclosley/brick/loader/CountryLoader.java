package io.github.followsclosley.brick.loader;

import io.github.followsclosley.brick.data.Country;
import io.github.followsclosley.brick.data.repository.CountryRepository;
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
public class CountryLoader implements ApplicationRunner {

    final Logger logger = LoggerFactory.getLogger(CountryLoader.class);

    @Value("${catalog.resources.country}")
    private Resource resource;

    @Autowired
    private CountryRepository repository;

    /**
     * A tab delimited file containing all countries.
     * Source: https://raw.githubusercontent.com/lukes/ISO-3166-Countries-with-Regional-Codes/master/all/all.csv
     *
     * name,alpha-2,alpha-3,country-code,iso_3166-2,region,sub-region,intermediate-region,region-code,sub-region-code,intermediate-region-code
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
                    Country country = new Country();
                    country.setName(record.get(0));
                    country.setAlpha2(record.get(1));
                    country.setAlpha3(record.get(2));
                    country.setRegion(record.get(5));
                    country.setSubRegion(record.get(6));
                    repository.save(country);
                    counter++;
                } catch (Exception e) {
                    logger.error("Error loading country: {}", record, e);
                }
            }
        }

        logger.info("Inserted {} countries.", counter);
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
