package io.github.followsclosley.brick.data;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class LegoPartRelationship {
    @Getter
    @RequiredArgsConstructor
    public enum RelationshipType {
        P("Print"), R("Pair"), B("Sub"), M("Mold"), T("Pattern"), A("Alternate");
        private final String display;
    }

    private RelationshipType type;
    @ManyToOne
    @JoinColumn(name = "child_id")
    private LegoPart child;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private LegoPart parent;
}
