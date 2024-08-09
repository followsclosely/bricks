package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.LegoTheme;
import io.github.followsclosley.brick.data.repository.LegoThemeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class LegoThemeLoader implements Processor {
    private final LegoThemeRepository repository;

    @Override
    public void process(Exchange exchange) throws IOException {

        CSVFormat csvParser = CSVFormat.DEFAULT.builder()
                .setHeader().setSkipHeaderRecord(true)
                .setDelimiter(',')
                .setIgnoreEmptyLines(true)
                .build();

        int counter = 0;
        Map<String, LegoTheme> cache = new HashMap<>();
        try (
                final InputStream in = exchange.getIn().getBody(InputStream.class);
                final Reader reader = new InputStreamReader(in)
        ) {
            for (CSVRecord record : csvParser.parse(reader)) {
                //id,name,parent_id
                LegoTheme legoTheme = new LegoTheme();
                legoTheme.setId(record.get(0));
                legoTheme.setName(record.get(1));

                String parentId = record.get(2);
                if(StringUtils.hasText(parentId)) {
                    LegoTheme parent = cache.get(parentId);
                    legoTheme.setParent(parent);
                }

                //Place the theme in the cache so that parent lookup can occur.
                cache.put(legoTheme.getId(), legoTheme);

                repository.save(legoTheme);
                counter++;
            }
        }

        log.info("Inserted/Updated {} themes.", counter);
    }
}