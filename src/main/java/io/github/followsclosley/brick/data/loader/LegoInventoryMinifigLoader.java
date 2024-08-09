package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.LegoInventory;
import io.github.followsclosley.brick.data.LegoInventoryMinifig;
import io.github.followsclosley.brick.data.LegoMinifig;
import io.github.followsclosley.brick.data.repository.LegoInventoryRepository;
import io.github.followsclosley.brick.data.repository.LegoMinifigRepository;
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
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LegoInventoryMinifigLoader implements Processor {

    private final LegoInventoryRepository legoInventoryRepository;
    private final LegoMinifigRepository legoMinifigRepository;

    @Override
    public void process(Exchange exchange) throws IOException {

        //inventory_id,fig_num,quantity

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
            String id = null;
            LegoInventory legoInventory = null;
            for (CSVRecord record : csvParser.parse(reader)) {

                if (id == null || !id.equals(record.get(0))) {
                    if( legoInventory != null) {
                        //Inventory changed, so persist.
                        legoInventoryRepository.save(legoInventory);
                        log.info("Inserted/Updated {} inventory/minifigs.", counter);
                    }
                    counter = 0;
                    id = record.get(0);
                    Optional<LegoInventory> optionalInventory = legoInventoryRepository.findById(record.get(0));
                    if (optionalInventory.isPresent()){
                        legoInventory = optionalInventory.get();
                    } else {
                        legoInventory = new LegoInventory();
                        legoInventory.setId(record.get(0));
                    }
                }

                Optional<LegoMinifig> optionalLegoMinifig = legoMinifigRepository.findById(record.get(1));
                if( optionalLegoMinifig.isPresent() ){
                    LegoInventoryMinifig legoInventoryMinifig = new LegoInventoryMinifig();
                    legoInventoryMinifig.setQuantity(Integer.parseInt(record.get(2)));
                    legoMinifigRepository.findById(record.get(1)).ifPresent(legoInventoryMinifig::setLegoMinifig);
                    legoInventory.getMinifigs().add(legoInventoryMinifig);
                }

                counter++;
            }

            if( legoInventory != null) {
                legoInventoryRepository.save(legoInventory);
                log.info("Inserted/Updated {} inventory/minifigs.", counter);
                counter = 0;
            }
        }
    }
}