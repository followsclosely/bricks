package io.github.followsclosley.brick.loader;

import com.google.common.primitives.Ints;
import io.github.followsclosley.brick.data.ChangeLogBuilder;
import io.github.followsclosley.brick.data.entity.LegoCategory;
import io.github.followsclosley.brick.data.entity.LegoColor;
import io.github.followsclosley.brick.data.entity.change.ChangeLog;
import io.github.followsclosley.brick.data.repository.ChangeLogRepository;
import io.github.followsclosley.brick.data.repository.LegoCategoryRepository;
import io.github.followsclosley.brick.data.repository.LegoColorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class LegoCategoryLoader {

    private final CSVFormat csvParser;
    private final LegoCategoryRepository legoCategoryRepository;
    private final ChangeLogRepository changeLogRepository;

    @ServiceActivator(inputChannel = "fileInputChannel-LegoCategory")
    public void processFile(File file) throws IOException {
        System.out.println("Processing file: " + file.getAbsolutePath());

        int counter = 0;

        ChangeLogBuilder<LegoCategory> changeLogBuilder = new ChangeLogBuilder<>();
        changeLogBuilder.setEntity(LegoCategory.class.getSimpleName());

        //Color ID	Color Name	RGB	Type	Parts	In Sets	Wanted	For Sale	Year From	Year To
        try (FileReader reader = new FileReader(file)) {
            for (CSVRecord record : csvParser.parse(reader)) {
                if(record.isSet(1)) {
                    LegoCategory category = new LegoCategory();
                    category.setId(record.get(0));
                    category.setName(record.get(1));

                    if (changeLogBuilder.compare(category, legoCategoryRepository.findById(category.getId()))) {
                        legoCategoryRepository.save(category);
                        counter++;
                    }
                }
            }
        }

        ChangeLog changeLog = changeLogBuilder.build();
        log.info("Processed {} {}(s), inserting {} and updating {}.", changeLogBuilder.getProcessed(), changeLog.getEntity(), changeLogBuilder.getCreated(), changeLogBuilder.getUpdate());
    }
}
