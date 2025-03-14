package io.github.followsclosley.brick.data.entity.change;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Collate;
import org.hibernate.annotations.Columns;
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

    @Column(length = 5000)
    private String message;
    public String getMessage(){
        return ( message == null || message.length() < 5000) ? message : StringUtils.truncate(message, 5000);
    }
}
