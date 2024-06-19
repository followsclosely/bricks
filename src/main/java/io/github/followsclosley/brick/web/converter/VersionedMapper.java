package io.github.followsclosley.brick.web.converter;

public interface VersionedMapper<T, R> {
    R map(T source);
    String getVersion();
}
