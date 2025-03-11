package io.github.followsclosley.brick.data.loader;

import io.github.followsclosley.brick.data.LegoMinifig;
import io.github.followsclosley.brick.data.repository.LegoMinifigRepository;
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
import java.util.zip.GZIPInputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class LegoMinifigLoader {
    @Value("${catalog.rebrickable.minifigs}")
    private String url;

    private final CSVFormat csvParser;
    private final LegoMinifigRepository legoMinifigRepository;

    public void process() throws IOException {
        log.info("Downloading {} ...", url);
        int counter = 0;
        try (final GZIPInputStream in = new GZIPInputStream(new URL(url).openStream());
             final Reader reader = new InputStreamReader(in))
        {
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