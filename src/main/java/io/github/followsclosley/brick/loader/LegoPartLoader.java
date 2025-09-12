package io.github.followsclosley.brick.loader;

import io.github.followsclosley.brick.data.ChangeLogBuilder;
import io.github.followsclosley.brick.data.entity.LegoCategory;
import io.github.followsclosley.brick.data.entity.LegoPart;
import io.github.followsclosley.brick.data.entity.change.ChangeLog;
import io.github.followsclosley.brick.data.repository.ChangeLogRepository;
import io.github.followsclosley.brick.data.repository.LegoCategoryRepository;
import io.github.followsclosley.brick.data.repository.LegoPartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class LegoPartLoader {

    private final CSVFormat csvParser;
    private final LegoPartRepository legoPartRepository;
    private final LegoCategoryRepository categoryRepository;

    private final ChangeLogRepository changeLogRepository;

    @ServiceActivator(inputChannel = "fileInputChannel-LegoPart")
    public void processFile(File file) throws IOException {
        System.out.println("Processing file: " + file.getAbsolutePath());

        int counter = 0;

        ChangeLogBuilder<LegoPart> changeLogBuilder = new ChangeLogBuilder<>();
        changeLogBuilder.setEntity(LegoPart.class.getSimpleName());

        Map<String, LegoCategory> categories = categoryRepository.findAll().stream().collect(Collectors.toMap(LegoCategory::getId, c -> c));

        //Category ID	Category Name	Number	Name	Alternate Item Number
        try (FileReader reader = new FileReader(file)) {
            for (CSVRecord record : csvParser.parse(reader)) {
                if (record.isSet(1)) {
                    LegoPart part = new LegoPart();
                    part.setLegoCategory(categories.get(record.get(0)));
                    //1: Category Name
                    part.setId(record.get(2));
                    part.setName(record.get(3));
                    part.setAlternate(record.get(4));

                    if (changeLogBuilder.compare(part, legoPartRepository.findById(part.getId()))) {
                        legoPartRepository.save(part);
                        counter++;
                    }
                }
            }
        }

        ChangeLog changeLog = changeLogBuilder.build();
        log.info("Processed {} {}(s), inserting {} and updating {}.", changeLogBuilder.getProcessed(), changeLog.getEntity(), changeLogBuilder.getCreated(), changeLogBuilder.getUpdate());
    }
}
