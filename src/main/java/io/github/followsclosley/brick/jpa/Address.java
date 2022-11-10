package io.github.followsclosley.brick.jpa;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

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

    @ManyToOne
    private Country country;
}
