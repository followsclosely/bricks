package io.github.followsclosley.brick.data.entity.change;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class ChangeLog {
    @Id
    @UuidGenerator
    private String id;
    private Instant dateTimeStart;
    private Instant dateTimeEnd;
    private String message;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Transient
    private List<ChangeLogLine> changeLogs;

    public static ChangeLog now() {
        ChangeLog changeLog = new ChangeLog();
        changeLog.changeLogs = new ArrayList<>();
        changeLog.setDateTimeStart(Instant.now());
        return changeLog;
    }

    @PrePersist
    private void prePersist() {
        if (dateTimeEnd == null) {
            dateTimeEnd = Instant.now();
        }
    }

    public void addLine(String message) {
        ChangeLogLine changeLogLine = new ChangeLogLine();
        changeLogLine.setDateTime(Instant.now());
        changeLogLine.setMessage(message);
        changeLogLine.setParent(this);
        changeLogs.add(changeLogLine);
    }

    public boolean hasChangeLogs() {
        return changeLogs != null && !changeLogs.isEmpty();
    }
}
