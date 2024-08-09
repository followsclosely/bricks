package io.github.followsclosley.brick.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LegoInventory {
    @Id
    private String id;
    private String version;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "lego_inventory_parts", joinColumns = @JoinColumn(name = "inventory_id"))
    private Set<LegoInventoryPart> parts;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "lego_inventory_minifigs", joinColumns = @JoinColumn(name = "inventory_id"))
    private Set<LegoInventoryMinifig> minifigs;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "set_id")
    private LegoSet legoSet;
}
