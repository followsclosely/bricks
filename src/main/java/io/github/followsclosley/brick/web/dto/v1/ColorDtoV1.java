package io.github.followsclosley.brick.web.dto.v1;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ColorDtoV1 {
    String id;
    String name;
    String rgb;
}
