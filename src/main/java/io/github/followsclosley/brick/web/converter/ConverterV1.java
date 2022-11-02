package io.github.followsclosley.brick.web.converter;

import io.github.followsclosley.brick.jpa.Franchise;
import io.github.followsclosley.brick.web.dto.CategoryDto;
import io.github.followsclosley.brick.web.dto.ColorDto;
import io.github.followsclosley.brick.web.dto.ElementDto;
import io.github.followsclosley.brick.web.dto.FranchiseDto;
import io.github.followsclosley.brick.web.dto.v1.CategoryDtoV1;
import io.github.followsclosley.brick.web.dto.v1.ColorDtoV1;
import io.github.followsclosley.brick.web.dto.v1.ElementDtoV1;
import io.github.followsclosley.brick.web.dto.v1.FranchiseDtoV1;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component("v1")
public class ConverterV1 extends ModelMapperConverter {
    public ConverterV1(){
        addMapping(CategoryDto.class, CategoryDtoV1.class);
        addMapping(ColorDto.class, ColorDtoV1.class);
        addMapping(ElementDto.class, ElementDtoV1.class);
        addMapping(FranchiseDto.class, FranchiseDtoV1.class);
    }
}