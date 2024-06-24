package io.github.followsclosley.brick.web.dto.v1;

import lombok.*;

@Data
public class SetDtoV1 {
    private String id;
    private String name;
    private int releaseYear;
    private ThemeDtoV1 theme;
    private int partCount;
    private String imageUrl;
}
