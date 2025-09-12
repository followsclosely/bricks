package io.github.followsclosley.brick.loader;

import com.google.common.primitives.Ints;
import io.github.followsclosley.brick.data.ChangeLogBuilder;
import io.github.followsclosley.brick.data.entity.LegoCategory;
import io.github.followsclosley.brick.data.entity.LegoInstructions;
import io.github.followsclosley.brick.data.entity.change.ChangeLog;
import io.github.followsclosley.brick.data.repository.ChangeLogRepository;
import io.github.followsclosley.brick.data.repository.LegoCategoryRepository;
import io.github.followsclosley.brick.data.repository.LegoInstructionsRepository;
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
public class LegoInstructionsLoader {

    private final CSVFormat csvParser;
    private final LegoInstructionsRepository legoInstructionsRepository;
    private final LegoCategoryRepository categoryRepository;
    private final ChangeLogRepository changeLogRepository;

    @ServiceActivator(inputChannel = "fileInputChannel-LegoInstructions")
    public void processFile(File file) throws IOException {
        System.out.println("Processing file: " + file.getAbsolutePath());

        int counter = 0;

        ChangeLogBuilder<LegoInstructions> changeLogBuilder = new ChangeLogBuilder<>();
        changeLogBuilder.setEntity(LegoInstructions.class.getSimpleName());

        Map<String, LegoCategory> categories = categoryRepository.findAll().stream().collect(Collectors.toMap(LegoCategory::getId, c -> c));

        //Category ID	Category Name	Number	Name	Year Released	Weight (in Grams)	Dimensions
        try (FileReader reader = new FileReader(file)) {
            for (CSVRecord record : csvParser.parse(reader)) {
                if (record.isSet(1)) {
                    LegoInstructions entity = new LegoInstructions();
                    entity.setLegoCategory(categories.get(record.get(0)));
                    //1: Category Name
                    entity.setId(record.get(2));
                    entity.setName(record.get(3));
                    entity.setReleaseYear(Ints.tryParse(record.get(4)));
                    if (record.isSet(5)) {
                        entity.setWeightInGrams(Ints.tryParse(record.get(5)));
                    }
                    if (record.isSet(6)) {
                        entity.setDimensions(record.get(6));
                    }

                    if (changeLogBuilder.compare(entity, legoInstructionsRepository.findById(entity.getId()))) {
                        legoInstructionsRepository.save(entity);
                        counter++;
                    }
                }
            }
        }

        ChangeLog changeLog = changeLogBuilder.build();
        log.info("Processed {} {}(s), inserting {} and updating {}.", changeLogBuilder.getProcessed(), changeLog.getEntity(), changeLogBuilder.getCreated(), changeLogBuilder.getUpdate());
    }
}
