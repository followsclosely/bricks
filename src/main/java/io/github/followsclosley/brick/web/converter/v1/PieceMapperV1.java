package io.github.followsclosley.brick.web.converter.v1;

import io.github.followsclosley.brick.data.Category;
import io.github.followsclosley.brick.data.Piece;
import io.github.followsclosley.brick.web.converter.VersionedMapper;
import io.github.followsclosley.brick.web.dto.v1.CategoryDtoV1;
import io.github.followsclosley.brick.web.dto.v1.PieceDtoV1;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface PieceMapperV1 extends VersionedMapper<Piece, PieceDtoV1> {
    @Override
    default String getVersion() {
        return "1.0";
    }
}
