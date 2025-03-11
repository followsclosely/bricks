package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.LegoColor;
import io.github.followsclosley.brick.data.LegoInventory;
import io.github.followsclosley.brick.data.LegoInventoryPart;
import io.github.followsclosley.brick.data.LegoPart;
import io.github.followsclosley.brick.data.repository.LegoColorRepository;
import io.github.followsclosley.brick.data.repository.LegoInventoryRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class LegoInventoryPartLoader {
    @Value("${catalog.rebrickable.inventories-parts}")
    private String url;

    private final CSVFormat csvParser;
    private final LegoInventoryRepository legoInventoryRepository;
    private final LegoPartRepository legoPartRepository;
    private final LegoColorRepository legoColorRepository;

    public void process() throws IOException {
        //inventory_id,part_num,color_id,quantity,is_spare,img_url
        log.info("Loading all the colors from the database ...");
        Map<String, LegoColor> colors = legoColorRepository.findAll().stream().collect(Collectors.toMap(LegoColor::getId, c -> c));

        int counter = 0;
        try (final GZIPInputStream in = new GZIPInputStream(new URL(url).openStream());
             final Reader reader = new InputStreamReader(in))
        {
            String id = null;
            LegoInventory legoInventory = null;
            for (CSVRecord record : csvParser.parse(reader)) {

                if (id == null || !id.equals(record.get(0))) {
                    if( legoInventory != null) {
                        //Inventory changed, so persist.
                        legoInventoryRepository.save(legoInventory);
                        //log.info("Inserted/Updated {} inventory/parts.", counter);
                    }
                    counter = 0;
                    id = record.get(0);
                    Optional<LegoInventory> optionalInventory = legoInventoryRepository.findById(record.get(0));
                    if (optionalInventory.isPresent()){
                        legoInventory = optionalInventory.get();
                    } else {
                        legoInventory = new LegoInventory();
                        legoInventory.setId(record.get(0));
                        //legoInventoryRepository.save(legoInventory);
                    }
                } else {

                }

                Optional<LegoPart> optionalPart = legoPartRepository.findById(record.get(1));
                if( optionalPart.isPresent() ){
                    LegoInventoryPart inventoryPart = new LegoInventoryPart();
                    //inventory_id,part_num,color_id,quantity,is_spare,img_url
                    //inventoryPart.setId(new LegoInventoryPart.InventoryPartId(record.get(0), record.get(1), record.get(2), Boolean.parseBoolean(record.get(4))));
                    //inventoryPart.setLegoInventory(legoInventory);
                    inventoryPart.setColor(colors.get(record.get(2)));
                    inventoryPart.setQuantity(Integer.parseInt(record.get(3)));
                    inventoryPart.setSpare(Boolean.parseBoolean(record.get(4)));
                    legoPartRepository.findById(record.get(1)).ifPresent(inventoryPart::setLegoPart);
                    legoInventory.getParts().add(inventoryPart);
                }

                counter++;
            }

            if( legoInventory != null) {
                legoInventoryRepository.save(legoInventory);
                log.info("Inserted/Updated {} inventory/parts.", counter);
                counter = 0;
            }
        }
    }
}