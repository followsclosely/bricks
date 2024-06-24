package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.Category;
import io.github.followsclosley.brick.data.Part;
import io.github.followsclosley.brick.data.repository.CategoryRepository;
import io.github.followsclosley.brick.data.repository.PartRepository;
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
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartLoader implements Processor {
    private final PartRepository partRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void process(Exchange exchange) throws Exception {

        CSVFormat csvParser = CSVFormat.DEFAULT.builder()
                .setHeader().setSkipHeaderRecord(true)
                .setDelimiter(',')
                .setIgnoreEmptyLines(true)
                .build();

        int counter = 0;

        Map<String, Category> categories = categoryRepository.findAll().stream().collect(Collectors.toMap(Category::getId, c -> c));

        try (
                final InputStream in = exchange.getIn().getBody(InputStream.class);
                final Reader reader = new InputStreamReader(in)
        ) {
            for (CSVRecord record : csvParser.parse(reader)) {
                //id,name,parent_id
                Part part = new Part();
                part.setId(record.get(0));
                part.setName(record.get(1));
                part.setCategory(categories.get(record.get(2)));
                part.setMaterial(record.get(3));

                partRepository.save(part);
                counter++;
            }
        }

        log.info("Inserted/Updated {} parts.", counter);
    }
}