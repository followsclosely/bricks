package io.github.followsclosley.brick.loader;

import com.google.common.primitives.Ints;
import io.github.followsclosley.brick.data.ChangeLogBuilder;
import io.github.followsclosley.brick.data.entity.LegoCategory;
import io.github.followsclosley.brick.data.entity.LegoSet;
import io.github.followsclosley.brick.data.entity.change.ChangeLog;
import io.github.followsclosley.brick.data.repository.ChangeLogRepository;
import io.github.followsclosley.brick.data.repository.LegoCategoryRepository;
import io.github.followsclosley.brick.data.repository.LegoSetRepository;
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
public class LegoSetLoader {

    private final CSVFormat csvParser;
    private final LegoSetRepository legoSetRepository;
    private final LegoCategoryRepository categoryRepository;

    private final ChangeLogRepository changeLogRepository;

    @ServiceActivator(inputChannel = "fileInputChannel-LegoSet")
    public void processFile(File file) throws IOException {
        System.out.println("Processing file: " + file.getAbsolutePath());

        int counter = 0;

        ChangeLogBuilder<LegoSet> changeLogBuilder = new ChangeLogBuilder<>();
        changeLogBuilder.setEntity(LegoSet.class.getSimpleName());

        Map<String, LegoCategory> categories = categoryRepository.findAll().stream().collect(Collectors.toMap(LegoCategory::getId, c -> c));

        //Category ID	Category Name	Number	Name	Year Released	Weight (in Grams)	Dimensions
        try (FileReader reader = new FileReader(file)) {
            for (CSVRecord record : csvParser.parse(reader)) {
                if (record.isSet(1)) {
                    LegoSet set = new LegoSet();
                    set.setLegoCategory(categories.get(record.get(0)));
                    //1: Category Name
                    set.setId(record.get(2));
                    set.setName(record.get(3));
                    set.setReleaseYear(Ints.tryParse(record.get(4)));
                    if (record.isSet(5)) {
                        set.setWeightInGrams(Ints.tryParse(record.get(5)));
                    }
                    if (record.isSet(6)) {
                        set.setDimensions(record.get(6));
                    }

                    if (changeLogBuilder.compare(set, legoSetRepository.findById(set.getId()))) {
                        legoSetRepository.save(set);
                        counter++;
                    }
                }
            }
        }

        ChangeLog changeLog = changeLogBuilder.build();
        log.info("Processed {} {}(s), inserting {} and updating {}.", changeLogBuilder.getProcessed(), changeLog.getEntity(), changeLogBuilder.getCreated(), changeLogBuilder.getUpdate());
    }
}
