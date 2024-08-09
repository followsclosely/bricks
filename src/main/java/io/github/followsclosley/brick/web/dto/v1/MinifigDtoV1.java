package io.github.followsclosley.brick.web.dto.v1;

import lombok.Data;

@Data
public class MinifigDtoV1 {
    private String id;
    private String name;
    private int partCount;
    private String imageUrl;
}
