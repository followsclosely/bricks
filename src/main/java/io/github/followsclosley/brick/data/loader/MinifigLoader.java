package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.Minifig;
import io.github.followsclosley.brick.data.Set;
import io.github.followsclosley.brick.data.Theme;
import io.github.followsclosley.brick.data.repository.MinifigRepository;
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
public class MinifigLoader implements Processor {
    private final MinifigRepository minifigRepository;

    @Override
    public void process(Exchange exchange) throws Exception {

        CSVFormat csvParser = CSVFormat.DEFAULT.builder()
                .setHeader().setSkipHeaderRecord(true)
                .setDelimiter(',')
                .setIgnoreEmptyLines(true)
                .build();

        int counter = 0;

        try (
                final InputStream in = exchange.getIn().getBody(InputStream.class);
                final Reader reader = new InputStreamReader(in)
        ) {
            for (CSVRecord record : csvParser.parse(reader)) {
                //fig_num,name,num_parts,img_url
                Minifig fig = new Minifig();
                fig.setId(record.get(0));
                fig.setName(record.get(1));
                fig.setPartCount(Integer.parseInt(record.get(2)));
                fig.setImageUrl(record.get(3));

                minifigRepository.save(fig);
                counter++;
            }
        }

        log.info("Inserted/Updated {} figs.", counter);
    }
}