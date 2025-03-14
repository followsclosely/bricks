package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.ChangeLogBuilder;
import io.github.followsclosley.brick.data.entity.LegoInventory;
import io.github.followsclosley.brick.data.entity.LegoInventoryMinifig;
import io.github.followsclosley.brick.data.entity.LegoMinifig;
import io.github.followsclosley.brick.data.entity.change.ChangeLog;
import io.github.followsclosley.brick.data.repository.*;
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
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class LegoInventoryMinifigLoader {
    private final CSVFormat csvParser;
    private final LegoInventoryRepository legoInventoryRepository;
    private final LegoMinifigRepository legoMinifigRepository;
    private final LegoColorRepository legoColorRepository;
    private final ChangeLogRepository changeLogRepository;

    @Value("${catalog.rebrickable.inventories-parts}")
    private String url;

    public void process() throws IOException {
        log.info("Downloading {} ...", url);

        int counter = 0;
        int setsModified = 0;
        int partsAdded = 0;
        int partsRemoved = 0;

        try (final GZIPInputStream in = new GZIPInputStream(new URL(url).openStream());
             final Reader reader = new InputStreamReader(in)) {

            ChangeLogBuilder<LegoInventory> changeLogBuilder = new ChangeLogBuilder<>();
            changeLogBuilder.setEntity("Lego Inventory Minifig");

            String id = null;
            boolean inventoryChanged = false;
            LegoInventory legoInventory = null;
            final Set<String> minifigsLoadedFromFile = new HashSet<>();
            //All the minifigs according to the database.
            Map<String, LegoInventoryMinifig> legoInventoryMinifigsByCompositeId = new HashMap<>();

            for (CSVRecord record : csvParser.parse(reader)) {

                if (id == null || !id.equals(record.get(0))) {
                    if (legoInventory != null) {

                        //compare the database values with the partsLoadedFromFile
                        Set<String> delta = legoInventoryMinifigsByCompositeId.keySet().stream()
                                .filter(p -> !minifigsLoadedFromFile.contains(p))
                                .collect(Collectors.toSet());

                        if( !delta.isEmpty()){
                            inventoryChanged = true;
                            //Remove an entire row of parts from the database that are not in partsLoadedFromFile
                            for( String compositeId: delta){
                                LegoInventoryMinifig part = legoInventoryMinifigsByCompositeId.get(compositeId);
                                legoInventory.getMinifigs().remove(part);
                                partsRemoved += part.getQuantity();
                                //log.info("Removed part {} from inventory: {}", compositeId, part);
                            }
                        }

                        if( inventoryChanged ) {
                            //Inventory changed, so persist.
                            legoInventoryRepository.save(legoInventory);
                            setsModified++;
                            //log.info("LegoInventory: Inventory for set {} updated - {} parts added and {} removed.", (legoInventory.getLegoSet()==null)?null:legoInventory.getLegoSet().getId(), partsAdded, partsRemoved);
                            changeLogBuilder.addLine(String.format("LegoInventory: Inventory for set %s updated - %d parts added and %d removed.", (legoInventory.getLegoSet()==null)?null:legoInventory.getLegoSet().getId(), partsAdded, partsRemoved));
                        } else {
                            //log.info("LegoInventory: Inventory for set {} did not change.", legoInventory.getLegoSet().getId());
                        }

                        counter++;
                        if (counter % 1000 == 0) {
                            log.info("Processed {} inventories.", counter);
                        }
                    }

                    Optional<LegoInventory> optionalInventory = legoInventoryRepository.findById(id = record.get(0));
                    if (optionalInventory.isPresent()) {
                        inventoryChanged = false;
                        legoInventory = optionalInventory.get();
                        legoInventoryMinifigsByCompositeId = legoInventory.getMinifigs().stream().collect(Collectors.toMap(LegoInventoryMinifig::getCompositeKey, Function.identity()));
                        minifigsLoadedFromFile.clear();
                        partsAdded = 0;
                        partsRemoved = 0;

                    } else {
                        inventoryChanged = true;
                        legoInventory = new LegoInventory();
                        legoInventory.setParts(new HashSet<>());
                        legoInventory.setId(record.get(0));
                    }
                }

                Optional<LegoMinifig> optionalPart = legoMinifigRepository.findById(record.get(1));
                if (optionalPart.isPresent()) {
                    //inventory_id,part_num,color_id,quantity,is_spare,img_url
                    LegoInventoryMinifig legoInventoryMinifig = new LegoInventoryMinifig();
                    legoInventoryMinifig.setQuantity(Integer.parseInt(record.get(2)));
                    legoMinifigRepository.findById(record.get(1)).ifPresent(legoInventoryMinifig::setLegoMinifig);

                    //Add the part to the list for comparison to find parts that may need to be removed.
                    minifigsLoadedFromFile.add(legoInventoryMinifig.getCompositeKey());

                    LegoInventoryMinifig partInDatabase = legoInventoryMinifigsByCompositeId.get(legoInventoryMinifig.getCompositeKey());

                    if( partInDatabase == null){
                        inventoryChanged = true;
                        legoInventory.getMinifigs().add(legoInventoryMinifig);
                        partsAdded += legoInventoryMinifig.getQuantity();
                        //log.info("Added part {} to inventory: {}", inventoryPart.getCompositeKey(), inventoryPart);
                    } else if (!partInDatabase.equals(legoInventoryMinifig)){
                        //The inventory has changed for this part!
                        inventoryChanged = true;
                        legoInventory.getMinifigs().remove(partInDatabase);
                        legoInventory.getMinifigs().add(legoInventoryMinifig);

                        int deltaInParts = (partInDatabase.getQuantity() - legoInventoryMinifig.getQuantity());
                        if( deltaInParts > 0 ){
                            partsAdded += deltaInParts;
                            //log.info("Added {} parts to inventory: {}", deltaInParts, inventoryPart);
                        } else if( deltaInParts < 0 ){
                            partsRemoved += deltaInParts;
                            //log.info("Removed {} parts from inventory: {}", deltaInParts, inventoryPart);
                        }
                    } else {
                        //The inventory has not changed!
                    }
                }
            }

            if (legoInventory != null) {
                legoInventoryRepository.save(legoInventory);
                log.info("Inserted/Updated {} Minifig Inventory(s).", setsModified);
            }

            ChangeLog changeLog = changeLogBuilder.build();
            changeLog.setMessage("Lego Minifig Inventory: " + ((changeLog.hasChangeLogs()) ? "Inserted/Updated " + setsModified + " " + "Minifigs(s)." : "No changes made."));
            changeLogRepository.save(changeLog);
        }
    }
}