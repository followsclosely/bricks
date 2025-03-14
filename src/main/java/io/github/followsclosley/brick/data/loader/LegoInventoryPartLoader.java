package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.ChangeLogBuilder;
import io.github.followsclosley.brick.data.entity.LegoColor;
import io.github.followsclosley.brick.data.entity.LegoInventory;
import io.github.followsclosley.brick.data.entity.LegoInventoryPart;
import io.github.followsclosley.brick.data.entity.LegoPart;
import io.github.followsclosley.brick.data.entity.change.ChangeLog;
import io.github.followsclosley.brick.data.repository.ChangeLogRepository;
import io.github.followsclosley.brick.data.repository.LegoColorRepository;
import io.github.followsclosley.brick.data.repository.LegoInventoryRepository;
import io.github.followsclosley.brick.data.repository.LegoPartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.CharSetUtils;
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
public class LegoInventoryPartLoader {
    private final CSVFormat csvParser;
    private final LegoInventoryRepository legoInventoryRepository;
    private final LegoPartRepository legoPartRepository;
    private final LegoColorRepository legoColorRepository;
    private final ChangeLogRepository changeLogRepository;

    @Value("${catalog.rebrickable.inventories-parts}")
    private String url;

    public void process() throws IOException {
        //inventory_id,part_num,color_id,quantity,is_spare,img_url
        log.info("Loading all the colors from the database ...");
        Map<String, LegoColor> colors = legoColorRepository.findAll().stream().collect(Collectors.toMap(LegoColor::getId, c -> c));

        int counter = 0;
        int setsModified = 0;
        int partsAdded = 0;
        int partsRemoved = 0;

        try (final GZIPInputStream in = new GZIPInputStream(new URL(url).openStream());
             final Reader reader = new InputStreamReader(in)) {

            ChangeLogBuilder<LegoInventory> changeLogBuilder = new ChangeLogBuilder<>();

            String id = null;
            boolean inventoryChanged = false;
            LegoInventory legoInventory = null;
            //LegoPart.id + LegoColor.id as key
            final Set<String> partsLoadedFromFile = new HashSet<>();
            //All the parts according to the database.
            Map<String, LegoInventoryPart> legoInventoryPartByCompositeId = new HashMap<>();

            for (CSVRecord record : csvParser.parse(reader)) {

                if (id == null || !id.equals(record.get(0))) {
                    if (legoInventory != null) {

                        //compare the database values with the partsLoadedFromFile
                        Set<String> delta = legoInventoryPartByCompositeId.keySet().stream()
                                .filter(p -> !partsLoadedFromFile.contains(p))
                                .collect(Collectors.toSet());

                        if( !delta.isEmpty()){
                            inventoryChanged = true;
                            //Remove an entire row of parts from the database that are not in partsLoadedFromFile
                            for( String compositeId: delta){
                                LegoInventoryPart part = legoInventoryPartByCompositeId.get(compositeId);
                                legoInventory.getParts().remove(part);
                                partsRemoved += part.getQuantity();
                                //log.info("Removed part {} from inventory: {}", compositeId, part);
                            }
                        }

                        if( inventoryChanged ) {
                            //Inventory changed, so persist.
                            legoInventoryRepository.save(legoInventory);
                            setsModified++;
                            //log.info("LegoInventory: Inventory for set {} updated - {} parts added and {} removed.", (legoInventory.getLegoSet()==null)?null:legoInventory.getLegoSet().getId(), partsAdded, partsRemoved);
                            changeLogBuilder.addLine(String.format("LegoInventory: Inventory for set %s updated - %n parts added and %n removed.", (legoInventory.getLegoSet()==null)?null:legoInventory.getLegoSet().getId(), partsAdded, partsRemoved));
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
                        legoInventoryPartByCompositeId = legoInventory.getParts().stream().collect(Collectors.toMap(LegoInventoryPart::getCompositeKey, Function.identity()));
                        partsLoadedFromFile.clear();
                        partsAdded = 0;
                        partsRemoved = 0;

                    } else {
                        inventoryChanged = true;
                        legoInventory = new LegoInventory();
                        legoInventory.setParts(new HashSet<>());
                        legoInventory.setId(record.get(0));
                    }
                }

                Optional<LegoPart> optionalPart = legoPartRepository.findById(record.get(1));
                if (optionalPart.isPresent()) {
                    //inventory_id,part_num,color_id,quantity,is_spare,img_url
                    LegoInventoryPart inventoryPart = new LegoInventoryPart();
                    inventoryPart.setColor(colors.get(record.get(2)));
                    inventoryPart.setQuantity(Integer.parseInt(record.get(3)));
                    inventoryPart.setSpare(Boolean.parseBoolean(record.get(4)));
                    legoPartRepository.findById(record.get(1)).ifPresent(inventoryPart::setLegoPart);

                    //Add the part to the list for comparison to find parts that may need to be removed.
                    partsLoadedFromFile.add(inventoryPart.getCompositeKey());

                    LegoInventoryPart partInDatabase = legoInventoryPartByCompositeId.get(inventoryPart.getCompositeKey());

                    if( partInDatabase == null){
                        inventoryChanged = true;
                        legoInventory.getParts().add(inventoryPart);
                        partsAdded += inventoryPart.getQuantity();
                        //log.info("Added part {} to inventory: {}", inventoryPart.getCompositeKey(), inventoryPart);
                    } else if (!partInDatabase.equals(inventoryPart)){
                        //The inventory has changed for this part!
                        inventoryChanged = true;
                        legoInventory.getParts().remove(partInDatabase);
                        legoInventory.getParts().add(inventoryPart);

                        int deltaInParts = (partInDatabase.getQuantity() - inventoryPart.getQuantity());
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
                log.info("Inserted/Updated {} Part Inventory(s).", setsModified);
            }

            ChangeLog changeLog = changeLogBuilder.build();
            changeLog.setMessage("LegoInventory: " + ((changeLog.hasChangeLogs()) ? "Inserted/Updated " + setsModified + " " + "LegoInventory(s)." : "No changes made."));
            changeLogRepository.save(changeLog);
        }
    }
}