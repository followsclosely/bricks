package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.ChangeLogBuilder;
import io.github.followsclosley.brick.data.entity.change.ChangeLog;
import io.github.followsclosley.brick.data.repository.ChangeLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.zip.GZIPInputStream;

@Slf4j
@RequiredArgsConstructor
public abstract class BasicAbstractLoader<E, PK> {
    private final String entityType;
    private final String url;
    private final CSVFormat csvParser;
    private final JpaRepository<E, PK> repository;
    private final ChangeLogRepository changeLogRepository;

    public void process() throws IOException {
        log.info("Downloading {} ...", url);
        int counter = 0;
        try (final GZIPInputStream in = new GZIPInputStream(new URL(url).openStream());
             final Reader reader = new InputStreamReader(in)) {

            preLoad();

            ChangeLogBuilder<E> changeLogBuilder = new ChangeLogBuilder<>();
            changeLogBuilder.setEntity(entityType);

            for (CSVRecord record : csvParser.parse(reader)) {
                E entity = map(record);

                //log.info("Saving Category: {}", category);
                if (changeLogBuilder.compare(entity, repository.findById(getId(entity)))) {
                    repository.save(entity);
                    counter++;
                }
            }

            ChangeLog changeLog = changeLogBuilder.build();
            changeLog.setMessage(entityType + ": " + ((changeLog.hasChangeLogs()) ? "Inserted/Updated " + counter + " " + entityType + "(s)." : "No changes made."));
            changeLogRepository.save(changeLog);

            postLoad();
        }

        log.info("Inserted/Updated {} " + entityType + "(s).", counter);
    }

    public void preLoad(){}
    abstract E map(CSVRecord record);
    abstract PK getId(E entity);
    public void postLoad(){}
}