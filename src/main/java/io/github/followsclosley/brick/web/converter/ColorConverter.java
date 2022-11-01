package io.github.followsclosley.brick.web.converter;

import io.github.followsclosley.brick.jpa.Color;
import io.github.followsclosley.brick.web.pojo.ColorDto;
import org.springframework.stereotype.Component;

@Component
public class ColorConverter {
    public ColorDto convert(Color color) {
        return new ColorDto(color.getId(), color.getName(), color.getRgb());
    }
}
