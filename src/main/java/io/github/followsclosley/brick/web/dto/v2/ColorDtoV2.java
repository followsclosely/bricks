package io.github.followsclosley.brick.web.dto.v2;

import io.github.followsclosley.brick.web.dto.ColorDto;
import lombok.*;

@Getter
@Setter
@Builder @ToString
@NoArgsConstructor @AllArgsConstructor
public class ColorDtoV2 implements ColorDto {
    String id;
    String name;
    //String rgb; <-- !!This field has been removed!!
    final String someNewProperty = "Stink";
}
