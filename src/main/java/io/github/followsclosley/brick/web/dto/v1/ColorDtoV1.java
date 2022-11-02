package io.github.followsclosley.brick.web.dto.v1;

import io.github.followsclosley.brick.web.dto.ColorDto;
import lombok.*;

@Getter
@Setter
@Builder @ToString
@NoArgsConstructor @AllArgsConstructor
public class ColorDtoV1 implements ColorDto {
    String id;
    String name;
    String rgb;
}
