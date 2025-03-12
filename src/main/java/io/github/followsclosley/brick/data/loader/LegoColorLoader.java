package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.ChangeLogBuilder;
import io.github.followsclosley.brick.data.entity.LegoColor;
import io.github.followsclosley.brick.data.entity.change.ChangeLog;
import io.github.followsclosley.brick.data.repository.ChangeLogRepository;
import io.github.followsclosley.brick.data.repository.LegoColorRepository;
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
public class LegoColorLoader {
    @Value("${catalog.rebrickable.colors}")
    private String url;

    private final CSVFormat csvParser;
    private final LegoColorRepository repository;
    private final ChangeLogRepository changeLogRepository;

    public void process() throws IOException {
        log.info("Downloading {} ...", url);
        int counter = 0;
        try (final GZIPInputStream in = new GZIPInputStream(new URL(url).openStream());
             final Reader reader = new InputStreamReader(in))
        {
            //UPDATE LEGO_COLOR SET RGB='STINK' WHERE ID = 0
            ChangeLogBuilder<LegoColor> changeLogBuilder = new ChangeLogBuilder<>();

            for (CSVRecord record : csvParser.parse(reader)) {
                //id,name,rgb,is_trans
                LegoColor legoColor = new LegoColor();
                legoColor.setId(record.get(0));
                legoColor.setName(record.get(1));
                legoColor.setRgb(record.get(2));
                legoColor.setTransparent("t".equalsIgnoreCase(record.get(3)));

                if (changeLogBuilder.compare(legoColor, repository.findById(legoColor.getId()))) {
                    repository.save(legoColor);
                    counter++;
                }
            }

            ChangeLog changeLog = changeLogBuilder.build();
            changeLog.setMessage( "LegoColor: " + ((changeLog.hasChangeLogs()) ? "Inserted/Updated "+counter+" LegoColor(s)." : "No changes made."));
            changeLogRepository.save(changeLog);

        }

        log.info("Inserted/Updated {} colors.", counter);
    }
}