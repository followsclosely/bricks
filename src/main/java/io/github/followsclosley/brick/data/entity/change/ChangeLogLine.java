package io.github.followsclosley.brick.data.entity.change;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;

@Entity
@Data
public class ChangeLogLine {
    @Id
    @UuidGenerator
    private String id;

    @ManyToOne
    @JoinColumn(name = "change_log_id")
    private ChangeLog parent;

    private Instant dateTime;
    private String message;
}
