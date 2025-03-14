package io.github.followsclosley.brick.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Embeddable
@Data
public class LegoInventoryPart {

    @ManyToOne
    @JoinColumn(name = "part_id")
    private LegoPart legoPart;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private LegoColor color;

    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private boolean spare;

    public String getCompositeKey(){
        return (legoPart==null?"null":legoPart.getId()) + "." + (color==null?"null":color.getId() + "." + spare);
    }
//    public String getPartId(){
//        return (legoPart==null) ? null : legoPart.getId();
//    }
}
