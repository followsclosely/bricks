package io.github.followsclosley.brick.web.converter;

import io.github.followsclosley.brick.jpa.Element;
import io.github.followsclosley.brick.web.pojo.ElementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ElementConverter {

    @Autowired CategoryConverter categoryConverter;

    public ElementDto convert(Element p){
        return new ElementDto(p.getId(), p.getName(), categoryConverter.convert(p.getCategory()) );
    }
}
