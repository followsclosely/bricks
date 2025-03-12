package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.ChangeLogBuilder;
import io.github.followsclosley.brick.data.entity.LegoInventory;
import io.github.followsclosley.brick.data.entity.LegoMinifig;
import io.github.followsclosley.brick.data.entity.change.ChangeLog;
import io.github.followsclosley.brick.data.repository.ChangeLogRepository;
import io.github.followsclosley.brick.data.repository.LegoMinifigRepository;
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
public class LegoMinifigLoader {
    @Value("${catalog.rebrickable.minifigs}")
    private String url;

    private final CSVFormat csvParser;
    private final LegoMinifigRepository legoMinifigRepository;
    private final ChangeLogRepository changeLogRepository;

    public void process() throws IOException {
        log.info("Downloading {} ...", url);
        int counter = 0;
        try (final GZIPInputStream in = new GZIPInputStream(new URL(url).openStream());
             final Reader reader = new InputStreamReader(in))
        {
            ChangeLogBuilder<LegoMinifig> changeLogBuilder = new ChangeLogBuilder<>();

            for (CSVRecord record : csvParser.parse(reader)) {
                //fig_num,name,num_parts,img_url
                LegoMinifig legoMinifig = new LegoMinifig();
                legoMinifig.setId(record.get(0));
                legoMinifig.setName(record.get(1));
                legoMinifig.setPartCount(Integer.parseInt(record.get(2)));
                legoMinifig.setImageUrl(record.get(3));

                if (changeLogBuilder.compare(legoMinifig, legoMinifigRepository.findById(legoMinifig.getId()))) {
                    legoMinifigRepository.save(legoMinifig);
                    counter++;
                }
            }

            ChangeLog changeLog = changeLogBuilder.build();
            changeLog.setMessage( "LegoMinifig: " + ((changeLog.hasChangeLogs()) ? "Inserted/Updated "+counter+" LegoMinifig(s)." : "No changes made."));
            changeLogRepository.save(changeLog);
        }

        log.info("Inserted/Updated {} figs.", counter);
    }
}