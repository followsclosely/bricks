package io.github.followsclosley.brick.web.dto.v1;

import io.github.followsclosley.brick.data.LegoSet;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Set;

@Data
public class LegoInventoryDtoV1 {
    @Id
    private String id;
    private String version;
    private Set<LegoInventoryPartDtoV1> parts;
    private Set<LegoInventoryMinifigDtoV1> minifigs;
    private LegoSet legoSet;
}
