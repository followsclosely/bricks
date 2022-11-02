package io.github.followsclosley.brick.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class VersionedConverter implements Converter {

    private final Converter defaultConverter = new ConverterV1();

    @Autowired private Map<String, Converter> converters;

    public <P> P map(Object bean, Class<P> klass, String version){
        Converter converter = converters.getOrDefault(version, defaultConverter);
        return converter.map(bean, klass);
    }

    @Override
    public <P> P map(Object bean, Class<P> klass) {
        return defaultConverter.map(bean, klass);
    }
}
