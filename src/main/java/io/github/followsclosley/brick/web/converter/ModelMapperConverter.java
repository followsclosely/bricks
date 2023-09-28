package io.github.followsclosley.brick.web.converter;

import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * A base class for converters that use ModelMapper to map between objects.
 *
 * To use, subclass this class and override the {@link #addMapping(Class, Class)} method to
 * map between interfaces and their implementations.
 *
 * For example, to map between the User interface and the UserImpl implementation, you would add
 * the following mapping:
 *
 *  addMapping(User.class, UserImpl.class);
 *
 * Once you have added your mappings, you can use the map() method to map between objects.
 * The map() method will automatically use the appropriate mapping for the given type.
 */
public abstract class ModelMapperConverter implements Converter {
    protected final ModelMapper mapper = new ModelMapper();


    protected final Map<Class<?>, Class<?>> interfaceToVersionMap = new HashMap<>();

    /**
     * Maps the given object to the given class using ModelMapper.
     *
     * @param bean the object to be mapped
     * @param klass the class to which the object should be mapped
     * @return the mapped object
     */
    @Override
    public <P> P map(Object bean, Class<P> klass) {
        return mapper.map(bean, map(klass));
    }

    /**
     * Adds a mapping between an interface and its implementation.
     *
     * @param type the interface
     * @param version the implementation
     */
    protected void addMapping(Class<?> type, Class<?> version) {
        interfaceToVersionMap.put(type, version);
    }

    private Type map(Type type) {
        return interfaceToVersionMap.getOrDefault(type, type.getClass());
    }
}
