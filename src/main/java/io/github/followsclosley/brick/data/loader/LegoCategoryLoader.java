package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.LegoCategory;
import io.github.followsclosley.brick.data.repository.LegoCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

@Slf4j
@Service
@RequiredArgsConstructor
public class LegoCategoryLoader implements Processor {
    private final LegoCategoryRepository repository;

    @Override
    public void process(Exchange exchange) throws IOException {

        CSVFormat csvParser = CSVFormat.DEFAULT.builder()
                .setHeader().setSkipHeaderRecord(true)
                .setDelimiter(',')
                .setIgnoreEmptyLines(true)
                .build();

        int counter = 0;
        try (
                final InputStream in = exchange.getIn().getBody(InputStream.class);
                final Reader reader = new InputStreamReader(in)
        ) {
            for (CSVRecord record : csvParser.parse(reader)) {
                //id,name
                LegoCategory theme = new LegoCategory();
                theme.setId(record.get(0));
                theme.setName(record.get(1));

                repository.save(theme);
                counter++;
            }
        }

        log.info("Inserted/Updated {} categories.", counter);
    }
}