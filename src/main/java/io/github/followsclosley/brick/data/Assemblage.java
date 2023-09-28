package io.github.followsclosley.brick.data;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.Type;

import java.io.IOException;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Assemblage {
    @Id
    private String id;

    @Column
    private String name;

    @Column(columnDefinition = "json")
    @Type(JsonType.class)
    private AssemblageDetails details;
}
