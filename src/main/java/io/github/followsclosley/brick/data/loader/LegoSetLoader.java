package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.LegoSet;
import io.github.followsclosley.brick.data.LegoTheme;
import io.github.followsclosley.brick.data.repository.LegoSetRepository;
import io.github.followsclosley.brick.data.repository.LegoThemeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LegoSetLoader implements Processor {
    private final LegoSetRepository setRepository;
    private final LegoThemeRepository legoThemeRepository;

    @Override
    public void process(Exchange exchange) throws IOException {

        CSVFormat csvParser = CSVFormat.DEFAULT.builder()
                .setHeader().setSkipHeaderRecord(true)
                .setDelimiter(',')
                .setIgnoreEmptyLines(true)
                .build();

        int counter = 0;

        Map<String, LegoTheme> themes = legoThemeRepository.findAll().stream().collect(Collectors.toMap(LegoTheme::getId, t -> t));

        try (
                final InputStream in = exchange.getIn().getBody(InputStream.class);
                final Reader reader = new InputStreamReader(in)
        ) {
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