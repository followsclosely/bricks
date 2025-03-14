package io.github.followsclosley.brick.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
@Data
public class LegoCollection {
    @Id
    @UuidGenerator
    private String id;

    @Column
    private String description;

    //@OneToMany(mappedBy="parent")
    @OrderBy("index")
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "lego_collection_item", joinColumns = @JoinColumn(name = "lego_collection_id"))
    private List<LegoCollectionItem> items;

}
