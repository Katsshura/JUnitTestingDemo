package com.katsshura.demo.junit.core.service.task;

import com.katsshura.demo.junit.core.dto.task.TaskDTO;
import com.katsshura.demo.junit.core.mapper.TaskMapper;
import com.katsshura.demo.junit.core.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public TaskDTO createTask(final TaskDTO taskDTO) {
        final var task = taskMapper.toEntity(taskDTO);
        final var result = taskRepository.save(task);
        return taskMapper.toDto(result);
    }

    public List<TaskDTO> findAllTasksForUserId(final Long userId) {
        final var result = taskRepository.findByUserId(userId);
        return result.stream().map(taskMapper::toDto).collect(Collectors.toList());
    }
}
