package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.LegoMinifig;
import io.github.followsclosley.brick.data.repository.LegoMinifigRepository;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class LegoMinifigLoader implements Processor {
    private final LegoMinifigRepository legoMinifigRepository;

    @Override
    public void process(Exchange exchange) throws IOException {

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
                LegoMinifig fig = new LegoMinifig();
                fig.setId(record.get(0));
                fig.setName(record.get(1));
                fig.setPartCount(Integer.parseInt(record.get(2)));
                fig.setImageUrl(record.get(3));

                legoMinifigRepository.save(fig);
                counter++;
            }
        }

        log.info("Inserted/Updated {} figs.", counter);
    }
}