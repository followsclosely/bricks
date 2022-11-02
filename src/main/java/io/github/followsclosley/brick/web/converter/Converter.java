package io.github.followsclosley.brick.web.converter;

public interface Converter {
    <P> P map(Object bean, Class<P> klass);
}
