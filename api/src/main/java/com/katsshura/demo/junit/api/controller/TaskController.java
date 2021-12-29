package com.katsshura.demo.junit.api.controller;

import com.katsshura.demo.junit.api.response.ResponseBuilder;
import com.katsshura.demo.junit.core.dto.task.TaskDTO;
import com.katsshura.demo.junit.core.service.task.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user/task")
public class TaskController {

    private final TaskService taskService;
    private final ResponseBuilder responseBuilder;

    public TaskController(TaskService taskService, ResponseBuilder responseBuilder) {
        this.taskService = taskService;
        this.responseBuilder = responseBuilder;
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody final TaskDTO taskDTO) {
        log.info("Attempting to create task with info: {}", taskDTO);
        final var result = taskService.createTask(taskDTO);
        return responseBuilder.buildCreatedResponse(result.getId());
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> findTasksByUserId(@RequestParam final Long userId) {
        log.info("Attempting to find tasks for userId: {}", userId);
        final var result = taskService.findAllTasksForUserId(userId);
        return responseBuilder.buildCollectionResponseOkOrNoContent(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TaskDTO> findTaskByLocation(@PathVariable final Long id) {
        log.info("Attempting to find task with Id: {}", id);
        final var result = taskService.findTaskById(id);
        return responseBuilder.buildResponseOkOrNoContent(result);
    }
}
