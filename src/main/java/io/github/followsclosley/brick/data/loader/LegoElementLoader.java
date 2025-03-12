package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.ChangeLogBuilder;
import io.github.followsclosley.brick.data.entity.LegoCategory;
import io.github.followsclosley.brick.data.entity.LegoColor;
import io.github.followsclosley.brick.data.entity.LegoElement;
import io.github.followsclosley.brick.data.entity.change.ChangeLog;
import io.github.followsclosley.brick.data.repository.ChangeLogRepository;
import io.github.followsclosley.brick.data.repository.LegoColorRepository;
import io.github.followsclosley.brick.data.repository.LegoElementRepository;
import io.github.followsclosley.brick.data.repository.LegoPartRepository;
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
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class LegoElementLoader {
    @Value("${catalog.rebrickable.elements}")
    private String url;

    private final CSVFormat csvParser;
    private final LegoElementRepository legoElementRepository;
    private final LegoPartRepository legoPartRepository;
    private final LegoColorRepository legoColorRepository;
    private final ChangeLogRepository changeLogRepository;

    public void process() throws IOException {
        log.info("Loading all the colors from the database ...");
        Map<String, LegoColor> colors = legoColorRepository.findAll().stream().collect(Collectors.toMap(LegoColor::getId, c -> c));

        log.info("Downloading {} ...", url);
        int counter = 0;
        try (final GZIPInputStream in = new GZIPInputStream(new URL(url).openStream());
             final Reader reader = new InputStreamReader(in))
        {
            //element_id,part_num,color_id,design_id

            ChangeLogBuilder<LegoElement> changeLogBuilder = new ChangeLogBuilder<>();

            for (CSVRecord record : csvParser.parse(reader)) {
                LegoElement legoElement = new LegoElement();
                legoElement.setId(record.get(0));
                legoPartRepository.findById(record.get(1)).ifPresent(legoElement::setLegoPart);
                legoElement.setLegoColor(colors.get(record.get(2)));
                legoElement.setDesign(record.get(3));

                if (changeLogBuilder.compare(legoElement, legoElementRepository.findById(legoElement.getId()))) {
                    legoElementRepository.save(legoElement);
                    counter++;
                }

                if ( counter > 0 && counter%10000 == 0) {
                    log.info("Inserted/Updated {} elements...", counter);
                }
            }

            ChangeLog changeLog = changeLogBuilder.build();
            changeLog.setMessage( "LegoElement: "+ ((changeLog.hasChangeLogs()) ? "Inserted/Updated "+counter+" LegoElement(s)." : "No changes made."));
            changeLogRepository.save(changeLog);
        }

        log.info("Inserted/Updated {} elements.", counter);
    }
}
