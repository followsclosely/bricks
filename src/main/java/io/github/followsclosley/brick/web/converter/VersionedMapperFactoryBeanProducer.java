package io.github.followsclosley.brick.web.converter;

import io.github.followsclosley.brick.data.*;
import io.github.followsclosley.brick.web.converter.v1.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class VersionedMapperFactoryBeanProducer {

    @Bean
    public VersionedMapperFactory<LegoColor> getColorMapper(List<? extends VersionedMapper<LegoColor, ?>> mappers, ColorMapperV1 defaultMapper){
        return new VersionedMapperFactory<>(mappers, defaultMapper);
    }

    @Bean
    public VersionedMapperFactory<LegoTheme> getCategoryMapper(List<? extends VersionedMapper<LegoTheme, ?>> mappers, ThemeMapperV1 defaultMapper){
        return new VersionedMapperFactory<>(mappers, defaultMapper);
    }

    @Bean
    public VersionedMapperFactory<LegoElement> getElementMapper(List<? extends VersionedMapper<LegoElement, ?>> mappers, ElementMapperV1 defaultMapper){
        return new VersionedMapperFactory<>(mappers, defaultMapper);
    }

    @Bean
    public VersionedMapperFactory<LegoPart> getPartMapper(List<? extends VersionedMapper<LegoPart, ?>> mappers, PartMapperV1 defaultMapper){
        return new VersionedMapperFactory<>(mappers, defaultMapper);
    }

    @Bean
    public VersionedMapperFactory<LegoSet> getSetMapper(List<? extends VersionedMapper<LegoSet, ?>> mappers, SetMapperV1 defaultMapper){
        return new VersionedMapperFactory<>(mappers, defaultMapper);
    }

    @Bean
    public VersionedMapperFactory<LegoMinifig> getMinifigMapper(List<? extends VersionedMapper<LegoMinifig, ?>> mappers, MinifigMapperV1 defaultMapper){
        return new VersionedMapperFactory<>(mappers, defaultMapper);
    }

    @Bean
    public VersionedMapperFactory<LegoInventory> getLegoInventoryMapper(List<? extends VersionedMapper<LegoInventory, ?>> mappers, LegoInventoryMapperV1 defaultMapper){
        return new VersionedMapperFactory<>(mappers, defaultMapper);
    }
}
