package com.katsshura.demo.junit.core.util;

import com.katsshura.demo.junit.core.entities.task.TaskEntity;
import com.katsshura.demo.junit.core.entities.user.UserEntity;

import java.util.List;
import java.util.Optional;

public final class TaskTestUtil {

    public static List<TaskEntity> buildTaskEntityList(final Long userId, final String description, final Long id) {
        return List.of(
                buildTaskEntity(userId, description, id, false).get()
        );
    }

    public static Optional<TaskEntity> buildTaskEntity(final Long userId,
                                                 final String description,
                                                 final Long id,
                                                 final boolean nullable) {
        if (nullable) {
            return Optional.empty();
        }

        final var user = UserEntity.builder().build();
        user.setId(userId);

        final var task = TaskEntity.builder()
                .description(description)
                .user(user)
                .build();

        task.setId(id);

        return Optional.of(task);
    }

}
