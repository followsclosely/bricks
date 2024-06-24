package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.Category;
import io.github.followsclosley.brick.data.Part;
import io.github.followsclosley.brick.data.Set;
import io.github.followsclosley.brick.data.Theme;
import io.github.followsclosley.brick.data.repository.CategoryRepository;
import io.github.followsclosley.brick.data.repository.PartRepository;
import io.github.followsclosley.brick.data.repository.SetRepository;
import io.github.followsclosley.brick.data.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SetLoader implements Processor {
    private final SetRepository setRepository;
    private final ThemeRepository themeRepository;

    @Override
    public void process(Exchange exchange) throws Exception {

        CSVFormat csvParser = CSVFormat.DEFAULT.builder()
                .setHeader().setSkipHeaderRecord(true)
                .setDelimiter(',')
                .setIgnoreEmptyLines(true)
                .build();

        int counter = 0;

        Map<String, Theme> themes = themeRepository.findAll().stream().collect(Collectors.toMap(Theme::getId, t -> t));

        try (
                final InputStream in = exchange.getIn().getBody(InputStream.class);
                final Reader reader = new InputStreamReader(in)
        ) {
            for (CSVRecord record : csvParser.parse(reader)) {
                //set_num,name,year,theme_id,num_parts,img_url
                Set set = new Set();
                set.setId(record.get(0));
                set.setName(record.get(1));
                set.setReleaseYear(Integer.parseInt(record.get(2)));
                set.setTheme(themes.get(record.get(3)));
                set.setPartCount(Integer.parseInt(record.get(4)));
                set.setImageUrl(record.get(5));

                setRepository.save(set);
                counter++;
            }
        }

        log.info("Inserted/Updated {} sets.", counter);
    }
}