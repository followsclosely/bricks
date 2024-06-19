package io.github.followsclosley.brick.data;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.Type;

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
