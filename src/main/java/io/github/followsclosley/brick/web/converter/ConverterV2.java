package io.github.followsclosley.brick.web.converter;

import io.github.followsclosley.brick.web.dto.ColorDto;
import io.github.followsclosley.brick.web.dto.v2.ColorDtoV2;
import org.springframework.stereotype.Component;

@Component("v2")
public class ConverterV2 extends ConverterV1 {
    public ConverterV2(){
        addMapping(ColorDto.class, ColorDtoV2.class);
    }
}