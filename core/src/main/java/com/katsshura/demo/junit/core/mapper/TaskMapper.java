package com.katsshura.demo.junit.core.mapper;

import com.katsshura.demo.junit.core.dto.task.TaskDTO;
import com.katsshura.demo.junit.core.entities.task.TaskEntity;
import com.katsshura.demo.junit.core.entities.user.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {

    public TaskDTO toDto(final TaskEntity taskEntity) {
        if (taskEntity == null) {
            return null;
        }

        return TaskDTO
                .builder()
                .description(taskEntity.getDescription())
                .userId(taskEntity.getUser().getId())
                .build();
    }

    public TaskEntity toEntity(final TaskDTO taskDTO) {
        if (taskDTO == null) {
            return null;
        }

        final var user = UserEntity.builder().build();
        user.setId(taskDTO.getUserId());

        return TaskEntity
                .builder()
                .user(user)
                .description(taskDTO.getDescription())
                .build();
    }
}
