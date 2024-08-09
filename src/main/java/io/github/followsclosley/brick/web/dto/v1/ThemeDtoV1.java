package io.github.followsclosley.brick.web.dto.v1;

import lombok.Data;

@Data
public class ThemeDtoV1 {
    private String id;
    private String name;
    private ThemeDtoV1 parent;
}