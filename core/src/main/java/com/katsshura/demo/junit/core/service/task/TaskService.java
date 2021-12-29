package com.katsshura.demo.junit.core.service.task;

import com.katsshura.demo.junit.core.dto.task.TaskDTO;
import com.katsshura.demo.junit.core.exceptions.UserNotFoundException;
import com.katsshura.demo.junit.core.mapper.TaskMapper;
import com.katsshura.demo.junit.core.repository.TaskRepository;
import com.katsshura.demo.junit.core.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userRepository = userRepository;
    }

    public TaskDTO createTask(final TaskDTO taskDTO) {
        log.info("Creating task with info: {}", taskDTO);

        if (!userRepository.existsById(taskDTO.getUserId())) {
            log.error("Could not find user with id = {} in database!", taskDTO.getUserId());
            throw new UserNotFoundException(taskDTO.getUserId());
        }

        final var task = taskMapper.toEntity(taskDTO);
        final var result = taskRepository.save(task);
        return taskMapper.toDto(result);
    }

    public List<TaskDTO> findAllTasksForUserId(final Long userId) {
        final var result = taskRepository.findByUserId(userId);

        log.info("Listing result for search with userId = {} - Result: {}", userId, result);

        return result.stream().map(taskMapper::toDto).collect(Collectors.toList());
    }
}
