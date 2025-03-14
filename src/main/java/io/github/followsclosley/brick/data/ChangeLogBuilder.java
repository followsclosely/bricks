package io.github.followsclosley.brick.data;

import io.github.followsclosley.brick.data.entity.change.ChangeLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ReflectionDiffBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Optional;

@Slf4j
public class ChangeLogBuilder<E> {

    private final ChangeLog changeLog = ChangeLog.now();

    public boolean compare(E newEntity, Optional<E> oldEntity) {
        if (oldEntity.isEmpty()) {
            changeLog.addLine("Created new " + newEntity.getClass().getSimpleName() + ": " + newEntity);
            return true;
        } else if (!oldEntity.get().equals(newEntity)) {
            DiffResult<E> differences = new ReflectionDiffBuilder<>(oldEntity.get(), newEntity, ToStringStyle.SHORT_PREFIX_STYLE).build();
            changeLog.addLine(differences.toString());
            return true;
        }
        return false;
    }

    public ChangeLogBuilder<E> setEntity(String entity) {
        changeLog.setEntity(entity);
        return this;
    }
    public ChangeLogBuilder<E> setMessage(String message) {
        changeLog.setMessage(message);
        return this;
    }

    public ChangeLogBuilder<E> addLine(String message) {
        changeLog.addLine(message);
        return this;
    }

    public ChangeLog build() {
        if (changeLog.getMessage() == null) {
            changeLog.setMessage("No changes made.");
        }
        return changeLog;
    }
}
