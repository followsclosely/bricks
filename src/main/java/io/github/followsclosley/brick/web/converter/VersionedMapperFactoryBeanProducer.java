package io.github.followsclosley.brick.web.converter;

import io.github.followsclosley.brick.data.Category;
import io.github.followsclosley.brick.data.Color;
import io.github.followsclosley.brick.data.Element;
import io.github.followsclosley.brick.data.Piece;
import io.github.followsclosley.brick.web.converter.v1.CategoryMapperV1;
import io.github.followsclosley.brick.web.converter.v1.ColorMapperV1;
import io.github.followsclosley.brick.web.converter.v1.ElementMapperV1;
import io.github.followsclosley.brick.web.converter.v1.PieceMapperV1;
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
    public VersionedMapperFactory<Category> getCategoryMapper(List<? extends VersionedMapper<Category, ?>> mappers, CategoryMapperV1 defaultMapper){
        return new VersionedMapperFactory<Category>(mappers, defaultMapper);
    }

    @Bean
    public VersionedMapperFactory<Element> getElementMapper(List<? extends VersionedMapper<Element, ?>> mappers, ElementMapperV1 defaultMapper){
        return new VersionedMapperFactory<Element>(mappers, defaultMapper);
    }

    @Bean
    public VersionedMapperFactory<Piece> getPieceMapper(List<? extends VersionedMapper<Piece, ?>> mappers, PieceMapperV1 defaultMapper){
        return new VersionedMapperFactory<Piece>(mappers, defaultMapper);
    }
}
