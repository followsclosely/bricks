package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.entity.LegoColor;
import io.github.followsclosley.brick.data.entity.change.ChangeLog;
import io.github.followsclosley.brick.data.repository.ChangeLogRepository;
import io.github.followsclosley.brick.data.repository.LegoColorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ReflectionDiffBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.time.Instant;
import java.util.Optional;
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
            ChangeLog changeLog = ChangeLog.now();

            for (CSVRecord record : csvParser.parse(reader)) {
                //id,name,rgb,is_trans
                LegoColor legoColor = new LegoColor();
                legoColor.setId(record.get(0));
                legoColor.setName(record.get(1));
                legoColor.setRgb(record.get(2));
                legoColor.setTransparent("t".equalsIgnoreCase(record.get(3)));


                Optional<LegoColor> optional = repository.findById(legoColor.getId());
                if( optional.isEmpty() ) {
                    repository.save(legoColor);
                    counter++;
                    changeLog.addLine("Created new Color: " + legoColor);
                }
                else if( !optional.get().equals(legoColor)) {
                    DiffResult<LegoColor> differences = new ReflectionDiffBuilder<>(optional.get(), legoColor, ToStringStyle.SHORT_PREFIX_STYLE).build();
                    log.info(differences.toString());
                    changeLog.addLine(differences.toString());

                    repository.save(legoColor);
                    counter++;
                }
            }

            if (changeLog.hasChangeLogs()){
                changeLog.setMessage("Inserted/Updated "+counter+" colors.");
            } else {
                changeLog.setMessage("No changes made.");
            }
            changeLogRepository.save(changeLog);

        }

        log.info("Inserted/Updated {} colors.", counter);
    }
}