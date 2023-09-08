package io.github.followsclosley.brick.web.converter;

import io.github.followsclosley.brick.data.Franchise;
import io.github.followsclosley.brick.web.dto.CategoryDto;
import io.github.followsclosley.brick.web.dto.ColorDto;
import io.github.followsclosley.brick.web.dto.ElementDto;
import io.github.followsclosley.brick.web.dto.FranchiseDto;
import io.github.followsclosley.brick.web.dto.v1.CategoryDtoV1;
import io.github.followsclosley.brick.web.dto.v1.ElementDtoV1;
import io.github.followsclosley.brick.web.dto.v1.FranchiseDtoV1;
import io.github.followsclosley.brick.web.dto.v2.ColorDtoV2;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component("v2")
public class ConverterV2 extends ModelMapperConverter {
    public ConverterV2() {
        mapper.getConfiguration().setAmbiguityIgnored(true);

        addMapping(CategoryDto.class, CategoryDtoV1.class);

        addMapping(ColorDto.class, ColorDtoV2.class);

        addMapping(ElementDto.class, ElementDtoV1.class);

        addMapping(FranchiseDto.class, FranchiseDtoV1.class);
        mapper.addMappings(new PropertyMap<Franchise, FranchiseDtoV1>() {
            protected void configure() {
                skip().setWalls(null);
            }
        });
    }
}