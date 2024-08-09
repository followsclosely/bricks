package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.LegoColor;
import io.github.followsclosley.brick.data.repository.LegoColorRepository;
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
public class LegoColorLoader implements Processor {
    private final LegoColorRepository repository;

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
                //id,name,rgb,is_trans
                LegoColor legoColor = new LegoColor();
                legoColor.setId(record.get(0));
                legoColor.setName(record.get(1));
                legoColor.setRgb(record.get(2));
                legoColor.setTransparent("t".equalsIgnoreCase(record.get(3)));
                repository.save(legoColor);
                counter++;
            }
        }

        log.info("Inserted/Updated {} colors.", counter);
    }
}