package io.github.followsclosley.brick.web.dto.v1;

import io.github.followsclosley.brick.web.dto.ColorDto;
import lombok.*;

@Getter
@Setter
@Builder @ToString
@NoArgsConstructor @AllArgsConstructor
public class AddressDtoV1 implements ColorDto {
    private String city;

    /**
     * State (U.S.)
     * Province (Canada)
     * Federal District (Mexico)
     * County (U.K.)
     * etc...
     */
    private String district;

    /**
     * Zip (U.S.)
     * Postal Code (Canada, Mexico)
     * Postcode (U.K.)
     */
    private String postalArea;

    private String country;
}
