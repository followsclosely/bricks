package io.github.followsclosley.brick.loader;

import com.google.common.primitives.Ints;
import io.github.followsclosley.brick.data.ChangeLogBuilder;
import io.github.followsclosley.brick.data.entity.LegoColor;
import io.github.followsclosley.brick.data.entity.change.ChangeLog;
import io.github.followsclosley.brick.data.repository.ChangeLogRepository;
import io.github.followsclosley.brick.data.repository.LegoColorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class LegoColorLoader {

    private final CSVFormat csvParser;
    private final LegoColorRepository colorRepository;
    private final ChangeLogRepository changeLogRepository;

    @ServiceActivator(inputChannel = "fileInputChannel-LegoColor")
    public void processFile(File file) throws IOException {
        System.out.println("Processing file: " + file.getAbsolutePath());

        int counter = 0;

        ChangeLogBuilder<LegoColor> changeLogBuilder = new ChangeLogBuilder<>();
        changeLogBuilder.setEntity(LegoColor.class.getSimpleName());

        //Color ID	Color Name	RGB	Type	Parts	In Sets	Wanted	For Sale	Year From	Year To
        try (FileReader reader = new FileReader(file)) {
            for (CSVRecord record : csvParser.parse(reader)) {
                if(record.isSet(1)) {
                    LegoColor color = new LegoColor();
                    color.setId(record.get(0));
                    color.setName(record.get(1));
                    color.setRgb(record.get(2));
                    color.setType(record.get(3));
                    color.setParts(Ints.tryParse(record.get(4)));
                    color.setYearFrom(Ints.tryParse(record.get(8)));
                    color.setYearTo(Ints.tryParse(record.get(9)));

                    if (changeLogBuilder.compare(color, colorRepository.findById(color.getId()))) {
                        colorRepository.save(color);
                        counter++;
                    }
                }
            }
        }

        ChangeLog changeLog = changeLogBuilder.build();
        log.info("Processed {} {}(s), inserting {} and updating {}.", changeLogBuilder.getProcessed(), changeLog.getEntity(), changeLogBuilder.getCreated(), changeLogBuilder.getUpdate());
    }
}
