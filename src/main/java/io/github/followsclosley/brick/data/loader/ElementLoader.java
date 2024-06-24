package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.Color;
import io.github.followsclosley.brick.data.Element;
import io.github.followsclosley.brick.data.repository.ColorRepository;
import io.github.followsclosley.brick.data.repository.ElementRepository;
import io.github.followsclosley.brick.data.repository.PartRepository;
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
public class ElementLoader implements Processor {

    private final ElementRepository elementRepository;
    private final PartRepository partRepository;
    private final ColorRepository colorRepository;

    @Override
    public void process(Exchange exchange) throws Exception {

        CSVFormat csvParser = CSVFormat.DEFAULT.builder()
                .setHeader().setSkipHeaderRecord(true)
                .setDelimiter(',')
                .setIgnoreEmptyLines(true)
                .build();

        Map<String, Color> colors = colorRepository.findAll().stream().collect(Collectors.toMap(Color::getId, c -> c));

        int counter = 0;
        try (
                final InputStream in = exchange.getIn().getBody(InputStream.class);
                final Reader reader = new InputStreamReader(in)
        ) {
            //element_id,part_num,color_id,design_id
            for (CSVRecord record : csvParser.parse(reader)) {
                Element element = new Element();
                element.setId(record.get(0));
                partRepository.findById(record.get(1)).ifPresent(element::setPart);
                element.setColor(colors.get(record.get(2)));
                element.setDesign(record.get(3));
                elementRepository.save(element);
                counter++;
            }
        }

        log.info("Inserted/Updated {} elements.", counter);
    }
}
