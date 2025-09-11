package io.github.followsclosley.brick;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.AcceptOnceFileListFilter;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;

import java.io.File;
import java.io.IOException;

@Configuration
public class SpringIntegrationConfig {

    @Value("${catalog.bricklink.root-directory}")
    private String rootDirectory;

    @Bean
    @InboundChannelAdapter(value = "fileInputChannel-LegoColor", poller = @Poller(fixedDelay = "1000"))
    public FileReadingMessageSource bricklinkLegoColorLoader(
            @Value("${catalog.bricklink.colors}") String fileNamePattern
    ) throws IOException {
        return fileReadingMessageSource(rootDirectory, fileNamePattern);
    }

    @Bean
    @InboundChannelAdapter(value = "fileInputChannel-LegoCategory", poller = @Poller(fixedDelay = "1000"))
    public FileReadingMessageSource bricklinkLegoCategoryLoader(
            @Value("${catalog.bricklink.categories}") String fileNamePattern
    ) throws IOException {
        return fileReadingMessageSource(rootDirectory, fileNamePattern);
    }


    private FileReadingMessageSource fileReadingMessageSource(String rootDirectory, String fileNamePattern) throws IOException {
        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setDirectory(new File(rootDirectory));

        try (CompositeFileListFilter<File> filterList = new CompositeFileListFilter<File>()
                .addFilter(new SimplePatternFileListFilter(fileNamePattern))
                .addFilter(new AcceptOnceFileListFilter<>())) {
            source.setFilter(filterList);
        }

        return source;
    }

}