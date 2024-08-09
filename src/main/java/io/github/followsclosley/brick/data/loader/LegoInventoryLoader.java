package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.LegoInventory;
import io.github.followsclosley.brick.data.repository.LegoInventoryRepository;
import io.github.followsclosley.brick.data.repository.LegoSetRepository;
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
public class LegoInventoryLoader implements Processor {
    private final LegoSetRepository setRepository;
    private final LegoInventoryRepository legoInventoryRepository;

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
                //id,version,set_num
                LegoInventory legoInventory = new LegoInventory();
                legoInventory.setId(record.get(0));
                legoInventory.setVersion(record.get(1));

                setRepository.findById(record.get(2)).ifPresent(legoInventory::setLegoSet);
                //inventory.se

                legoInventoryRepository.save(legoInventory);
                counter++;
            }
        }

        log.info("Inserted/Updated {} inventories.", counter);
    }
}