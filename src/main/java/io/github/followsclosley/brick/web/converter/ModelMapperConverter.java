package io.github.followsclosley.brick.web.converter;

import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public abstract class ModelMapperConverter implements Converter {
    protected final ModelMapper mapper = new ModelMapper();

    protected final Map<Class<?>, Class<?>> interfaceToVersionMap = new HashMap<>();

    @Override
    public <P> P map(Object bean, Class<P> klass) {
        return mapper.map(bean, map(klass));
    }

    protected void addMapping(Class<?> type, Class<?> version) {
        interfaceToVersionMap.put(type, version);
    }

    private Type map(Type type) {
        return interfaceToVersionMap.getOrDefault(type, type.getClass());
    }
}
