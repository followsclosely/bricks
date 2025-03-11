package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.LegoCategory;
import io.github.followsclosley.brick.data.repository.LegoCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.zip.GZIPInputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class LegoCategoryLoader {
    @Value("${catalog.rebrickable.part-categories}")
    private String url;

    private final CSVFormat csvParser;
    private final LegoCategoryRepository repository;

    public void process() throws IOException {
        log.info("Downloading {} ...", url);
        int counter = 0;
        try (final GZIPInputStream in = new GZIPInputStream(new URL(url).openStream());
             final Reader reader = new InputStreamReader(in))
        {
            for (CSVRecord record : csvParser.parse(reader)) {
                //id,name
                LegoCategory category = new LegoCategory();
                category.setId(record.get(0));
                category.setName(record.get(1));

                //log.info("Saving Category: {}", category);
                repository.save(category);
                counter++;
            }
        }

        log.info("Inserted/Updated {} categories.", counter);
    }
}