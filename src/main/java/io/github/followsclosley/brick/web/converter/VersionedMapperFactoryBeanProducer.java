package io.github.followsclosley.brick.web.converter;

import io.github.followsclosley.brick.data.*;
import io.github.followsclosley.brick.web.converter.v1.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class VersionedMapperFactoryBeanProducer {

    @Bean
    public VersionedMapperFactory<Color> getColorMapper(List<? extends VersionedMapper<Color, ?>> mappers, ColorMapperV1 defaultMapper){
        return new VersionedMapperFactory<Color>(mappers, defaultMapper);
    }

    @Bean
    public VersionedMapperFactory<Theme> getCategoryMapper(List<? extends VersionedMapper<Theme, ?>> mappers, ThemeMapperV1 defaultMapper){
        return new VersionedMapperFactory<Theme>(mappers, defaultMapper);
    }

    @Bean
    public VersionedMapperFactory<Element> getElementMapper(List<? extends VersionedMapper<Element, ?>> mappers, ElementMapperV1 defaultMapper){
        return new VersionedMapperFactory<Element>(mappers, defaultMapper);
    }

    @Bean
    public VersionedMapperFactory<Part> getPartMapper(List<? extends VersionedMapper<Part, ?>> mappers, PartMapperV1 defaultMapper){
        return new VersionedMapperFactory<Part>(mappers, defaultMapper);
    }

    @Bean
    public VersionedMapperFactory<Set> getSetMapper(List<? extends VersionedMapper<Set, ?>> mappers, SetMapperV1 defaultMapper){
        return new VersionedMapperFactory<Set>(mappers, defaultMapper);
    }

    @Bean
    public VersionedMapperFactory<Minifig> getMinifigMapper(List<? extends VersionedMapper<Minifig, ?>> mappers, MinifigMapperV1 defaultMapper){
        return new VersionedMapperFactory<Minifig>(mappers, defaultMapper);
    }
}
