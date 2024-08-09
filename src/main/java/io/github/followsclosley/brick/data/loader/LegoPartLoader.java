package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.LegoCategory;
import io.github.followsclosley.brick.data.LegoPart;
import io.github.followsclosley.brick.data.repository.LegoCategoryRepository;
import io.github.followsclosley.brick.data.repository.LegoPartRepository;
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
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LegoPartLoader implements Processor {
    private final LegoPartRepository legoPartRepository;
    private final LegoCategoryRepository legoCategoryRepository;

    @Override
    public void process(Exchange exchange) throws IOException {

        CSVFormat csvParser = CSVFormat.DEFAULT.builder()
                .setHeader().setSkipHeaderRecord(true)
                .setDelimiter(',')
                .setIgnoreEmptyLines(true)
                .build();

        int counter = 0;

        Map<String, LegoCategory> categories = legoCategoryRepository.findAll().stream().collect(Collectors.toMap(LegoCategory::getId, c -> c));

        try (
                final InputStream in = exchange.getIn().getBody(InputStream.class);
                final Reader reader = new InputStreamReader(in)
        ) {
            for (CSVRecord record : csvParser.parse(reader)) {
                //id,name,parent_id
                LegoPart legoPart = new LegoPart();
                legoPart.setId(record.get(0));
                legoPart.setName(record.get(1));
                legoPart.setLegoCategory(categories.get(record.get(2)));
                legoPart.setMaterial(record.get(3));

                legoPartRepository.save(legoPart);
                counter++;
            }
        }

        log.info("Inserted/Updated {} parts.", counter);
    }
}