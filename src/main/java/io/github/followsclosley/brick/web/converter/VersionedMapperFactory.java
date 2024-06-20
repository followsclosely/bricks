package io.github.followsclosley.brick.web.converter;

import io.github.followsclosley.brick.data.Color;
import io.github.followsclosley.brick.web.dto.v1.ColorDtoV1;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class VersionedMapperFactory<T> {

    private final VersionedMapper<T, ?> defaultMapper;
    private final Map<String, VersionedMapper<T, ?>> mappers;

    public VersionedMapperFactory(List<?extends VersionedMapper<T, ?>> mappers, VersionedMapper<T, ?> defaultMapper){
        this.defaultMapper = defaultMapper;
        this.mappers = mappers.stream().collect(Collectors.toMap(VersionedMapper::getVersion, Function.identity()));
    }

    public Object map(T type, String version){
        VersionedMapper<T, ?> mapper = this.mappers.get(version);
        return mapper==null ? defaultMapper.map(type) : mapper.map(type);
    }
}
