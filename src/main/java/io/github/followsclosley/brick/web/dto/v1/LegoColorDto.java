package io.github.followsclosley.brick.web.dto.v1;

import lombok.Data;

@Data
public class LegoColorDto {
    private String id;
    private String name;
    private String rgb;
    private Boolean transparent;
}
