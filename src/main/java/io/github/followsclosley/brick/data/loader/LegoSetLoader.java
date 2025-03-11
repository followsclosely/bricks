package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.entity.LegoSet;
import io.github.followsclosley.brick.data.entity.LegoTheme;
import io.github.followsclosley.brick.data.repository.LegoSetRepository;
import io.github.followsclosley.brick.data.repository.LegoThemeRepository;
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
public class LegoSetLoader {
    @Value("${catalog.rebrickable.sets}")
    private String url;

    private final CSVFormat csvParser;
    private final LegoSetRepository setRepository;
    private final LegoThemeRepository legoThemeRepository;

    public void process() throws IOException {
        log.info("Loading all the themes from the database ...");
        Map<String, LegoTheme> themes = legoThemeRepository.findAll().stream().collect(Collectors.toMap(LegoTheme::getId, t -> t));

        log.info("Downloading {} ...", url);
        int counter = 0;
        try (final GZIPInputStream in = new GZIPInputStream(new URL(url).openStream());
             final Reader reader = new InputStreamReader(in))
        {
            for (CSVRecord record : csvParser.parse(reader)) {
                //set_num,name,year,theme_id,num_parts,img_url
                LegoSet legoSet = new LegoSet();
                legoSet.setId(record.get(0));
                legoSet.setName(record.get(1));
                legoSet.setReleaseYear(Integer.parseInt(record.get(2)));
                legoSet.setLegoTheme(themes.get(record.get(3)));
                legoSet.setPartCount(Integer.parseInt(record.get(4)));
                legoSet.setImageUrl(record.get(5));

                setRepository.save(legoSet);
                counter++;
            }
        }

        log.info("Inserted/Updated {} sets.", counter);
    }
}