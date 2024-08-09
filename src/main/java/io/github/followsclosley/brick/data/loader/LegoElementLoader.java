package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.LegoColor;
import io.github.followsclosley.brick.data.LegoElement;
import io.github.followsclosley.brick.data.repository.LegoColorRepository;
import io.github.followsclosley.brick.data.repository.LegoElementRepository;
import io.github.followsclosley.brick.data.repository.LegoPartRepository;
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
public class LegoElementLoader implements Processor {

    private final LegoElementRepository legoElementRepository;
    private final LegoPartRepository legoPartRepository;
    private final LegoColorRepository legoColorRepository;

    @Override
    public void process(Exchange exchange) throws IOException {

        CSVFormat csvParser = CSVFormat.DEFAULT.builder()
                .setHeader().setSkipHeaderRecord(true)
                .setDelimiter(',')
                .setIgnoreEmptyLines(true)
                .build();

        Map<String, LegoColor> colors = legoColorRepository.findAll().stream().collect(Collectors.toMap(LegoColor::getId, c -> c));

        int counter = 0;
        try (
                final InputStream in = exchange.getIn().getBody(InputStream.class);
                final Reader reader = new InputStreamReader(in)
        ) {
            //element_id,part_num,color_id,design_id
            for (CSVRecord record : csvParser.parse(reader)) {
                LegoElement legoElement = new LegoElement();
                legoElement.setId(record.get(0));
                legoPartRepository.findById(record.get(1)).ifPresent(legoElement::setLegoPart);
                legoElement.setLegoColor(colors.get(record.get(2)));
                legoElement.setDesign(record.get(3));
                legoElementRepository.save(legoElement);
                counter++;
            }
        }

        log.info("Inserted/Updated {} elements.", counter);
    }
}
