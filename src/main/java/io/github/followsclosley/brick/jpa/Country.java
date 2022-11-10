package io.github.followsclosley.brick.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    private String alpha2;
    @Id private String alpha3;
    private String name;
    private String region;
    private String subRegion;
}
