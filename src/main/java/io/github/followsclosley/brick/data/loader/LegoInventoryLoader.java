package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.ChangeLogBuilder;
import io.github.followsclosley.brick.data.entity.LegoElement;
import io.github.followsclosley.brick.data.entity.LegoInventory;
import io.github.followsclosley.brick.data.entity.change.ChangeLog;
import io.github.followsclosley.brick.data.repository.ChangeLogRepository;
import io.github.followsclosley.brick.data.repository.LegoInventoryRepository;
import io.github.followsclosley.brick.data.repository.LegoSetRepository;
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
public class LegoInventoryLoader {
    @Value("${catalog.rebrickable.inventories}")
    private String url;

    private final CSVFormat csvParser;
    private final LegoSetRepository setRepository;
    private final LegoInventoryRepository legoInventoryRepository;
    private final ChangeLogRepository changeLogRepository;

    public void process() throws IOException {
        log.info("Downloading {} ...", url);
        int counter = 0;
        try (final GZIPInputStream in = new GZIPInputStream(new URL(url).openStream());
             final Reader reader = new InputStreamReader(in))
        {
            ChangeLogBuilder<LegoInventory> changeLogBuilder = new ChangeLogBuilder<>();

            for (CSVRecord record : csvParser.parse(reader)) {
                //id,version,set_num
                LegoInventory legoInventory = new LegoInventory();
                legoInventory.setId(record.get(0));
                legoInventory.setVersion(record.get(1));

                setRepository.findById(record.get(2)).ifPresent(legoInventory::setLegoSet);

                if (changeLogBuilder.compare(legoInventory, legoInventoryRepository.findById(legoInventory.getId()))) {
                    legoInventoryRepository.save(legoInventory);
                    counter++;
                }
            }

            ChangeLog changeLog = changeLogBuilder.build();
            changeLog.setMessage( "LegoInventory: " + ((changeLog.hasChangeLogs()) ? "Inserted/Updated "+counter+" LegoInventory(s)." : "No changes made."));
            changeLogRepository.save(changeLog);
        }
        log.info("Inserted/Updated {} inventories.", counter);
    }
}