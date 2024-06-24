package io.github.followsclosley.brick.data;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class PartRelationship {
    @Getter
    @RequiredArgsConstructor
    public enum RelationshipType {
        P("Print"), R("Pair"), B("Sub"), M("Mold"), T("Pattern"), A("Alternate");
        private final String display;
    }

    private RelationshipType type;
    @ManyToOne
    @JoinColumn(name = "child_id")
    private Part child;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Part parent;
}
