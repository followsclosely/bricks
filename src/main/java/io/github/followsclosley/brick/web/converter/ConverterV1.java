package io.github.followsclosley.brick.web.converter;

import io.github.followsclosley.brick.data.Assemblage;
import io.github.followsclosley.brick.data.Franchise;
import io.github.followsclosley.brick.web.dto.*;
import io.github.followsclosley.brick.web.dto.v1.*;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component("v1")
public class ConverterV1 extends ModelMapperConverter {
    public ConverterV1() {
        addMapping(CategoryDto.class, CategoryDtoV1.class);
        addMapping(ColorDto.class, ColorDtoV1.class);
        addMapping(ElementDto.class, ElementDtoV1.class);
        addMapping(PieceDto.class, PieceDtoV1.class);
        addMapping(FranchiseDto.class, FranchiseDtoV1.class);
        addMapping(AssemblagePieceDtoV1.class, AssemblageDtoV1.class);
        addMapping(AssemblageDto.class, AssemblageDtoV1.class);

        mapper.addMappings(new PropertyMap<Franchise, FranchiseDtoV1>() {
            protected void configure() {
                skip().setWalls(null);
            }
        });
    }
}