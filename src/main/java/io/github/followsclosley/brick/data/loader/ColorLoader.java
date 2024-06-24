package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.Color;
import io.github.followsclosley.brick.data.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColorLoader implements Processor {
    private final ColorRepository repository;

    @Override
    public void process(Exchange exchange) throws Exception {

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
                Color color = new Color();
                color.setId(record.get(0));
                color.setName(record.get(1));
                color.setRgb(record.get(2));
                color.setTransparent("t".equalsIgnoreCase(record.get(3)));
                repository.save(color);
                counter++;
            }
        }

        log.info("Inserted/Updated {} colors.", counter);
    }
}