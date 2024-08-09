package io.github.followsclosley.brick.web.dto.v1;

import lombok.Data;

@Data
public class ColorDtoV1 {
    String id;
    String name;
    String rgb;
    Boolean transparent;
}
