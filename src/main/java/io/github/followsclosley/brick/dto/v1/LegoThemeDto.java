package io.github.followsclosley.brick.dto.v1;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LegoThemeDto {
    private String id;
    private String name;

    private LegoThemeDto parent;
    private List<LegoThemeDto> children = new ArrayList<>();
}