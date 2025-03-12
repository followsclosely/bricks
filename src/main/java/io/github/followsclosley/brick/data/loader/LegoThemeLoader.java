package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.ChangeLogBuilder;
import io.github.followsclosley.brick.data.entity.LegoSet;
import io.github.followsclosley.brick.data.entity.LegoTheme;
import io.github.followsclosley.brick.data.entity.change.ChangeLog;
import io.github.followsclosley.brick.data.repository.ChangeLogRepository;
import io.github.followsclosley.brick.data.repository.LegoThemeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ReflectionDiffBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.zip.GZIPInputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class LegoThemeLoader {
    @Value("${catalog.rebrickable.themes}")
    private String url;

    private final CSVFormat csvParser;
    private final LegoThemeRepository repository;
    private final ChangeLogRepository changeLogRepository;

    public void process() throws IOException {
        Map<String, LegoTheme> cache = new HashMap<>();

        log.info("Downloading themes.csv.gz ...");
        int counter = 0;
        try (final GZIPInputStream in = new GZIPInputStream(new URL(url).openStream());
             final Reader reader = new InputStreamReader(in))
        {
            ChangeLogBuilder<LegoTheme> changeLogBuilder = new ChangeLogBuilder<>();

            for (CSVRecord record : csvParser.parse(reader)) {
                //id,name,parent_id
                LegoTheme legoTheme = new LegoTheme();
                legoTheme.setId(record.get(0));
                legoTheme.setName(record.get(1));

                String parentId = record.get(2);
                if(StringUtils.hasText(parentId)) {
                    LegoTheme parent = cache.get(parentId);
                    legoTheme.setParent(parent);
                }

                if (changeLogBuilder.compare(legoTheme, repository.findById(legoTheme.getId()))) {
                    repository.save(legoTheme);
                    counter++;
                }

                //Place the theme in the cache so that parent lookup can occur.
                cache.put(legoTheme.getId(), legoTheme);
            }

            ChangeLog changeLog = changeLogBuilder.build();
            changeLog.setMessage( "LegoTheme: " + ((changeLog.hasChangeLogs()) ? "Inserted/Updated "+counter+" LegoTheme(s)." : "No changes made."));
            changeLogRepository.save(changeLog);
        }

        log.info("Inserted/Updated {} themes.", counter);
    }
}