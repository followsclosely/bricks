package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.LegoCategory;
import io.github.followsclosley.brick.data.LegoPart;
import io.github.followsclosley.brick.data.repository.LegoCategoryRepository;
import io.github.followsclosley.brick.data.repository.LegoPartRepository;
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
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class LegoPartLoader {

    @Value("${catalog.rebrickable.parts}")
    private String url;

    private final CSVFormat csvParser;
    private final LegoPartRepository legoPartRepository;
    private final LegoCategoryRepository legoCategoryRepository;

    public void process() throws IOException {
        log.info("Loading all the categories from the database ...");
        Map<String, LegoCategory> categories = legoCategoryRepository.findAll().stream().collect(Collectors.toMap(LegoCategory::getId, c -> c));

        log.info("Downloading {}} ...", url);
        int counter = 0;
        try (final GZIPInputStream in = new GZIPInputStream(new URL(url).openStream());
             final Reader reader = new InputStreamReader(in))
        {
            for (CSVRecord record : csvParser.parse(reader)) {
                //id,name,parent_id
                LegoPart legoPart = new LegoPart();
                legoPart.setId(record.get(0));
                legoPart.setName(record.get(1));
                legoPart.setLegoCategory(categories.get(record.get(2)));
                legoPart.setMaterial(record.get(3));

                //log.info("Loading Part: {}", legoPart);
                legoPartRepository.save(legoPart);
                counter++;

                if ( counter%10000 == 0) {
                    log.info("Inserted/Updated {} parts...", counter);
                }
            }
        }

        log.info("Inserted/Updated {} parts.", counter);
    }
}