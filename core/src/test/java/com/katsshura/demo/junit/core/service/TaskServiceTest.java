package com.katsshura.demo.junit.core.service;

import com.katsshura.demo.junit.core.dto.task.TaskDTO;
import com.katsshura.demo.junit.core.entities.task.TaskEntity;
import com.katsshura.demo.junit.core.entities.user.UserEntity;
import com.katsshura.demo.junit.core.exceptions.UserNotFoundException;
import com.katsshura.demo.junit.core.mapper.TaskMapper;
import com.katsshura.demo.junit.core.repository.TaskRepository;
import com.katsshura.demo.junit.core.repository.UserRepository;
import com.katsshura.demo.junit.core.service.task.TaskService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Spy
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @Captor
    private ArgumentCaptor<TaskEntity> taskArgCaptor;

    @Nested
    class CreateTask {

        @ParameterizedTest(name = "#[{index}] Should assert equals for object provided as argument to repository for" +
                " values: userId = {0} | description = {1}")
        @CsvFileSource(resources = "/csv/task/TaskTestValidEntries.csv", numLinesToSkip = 1)
        public void createValidTask(final Long userId, final String description) {
            final var task = TaskDTO.builder().description(description).userId(userId).build();
            final var taskEntity = taskMapper.toEntity(task);

            when(userRepository.existsById(userId)).thenReturn(true);
            when(taskRepository.save(taskEntity)).thenReturn(taskEntity);

            final var result = taskService.createTask(task);

            verify(taskRepository).save(taskArgCaptor.capture());

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(description, taskArgCaptor.getValue().getDescription()),
                    () -> assertEquals(description, result.getDescription()),
                    () -> assertEquals(userId, taskArgCaptor.getValue().getUser().getId()),
                    () -> assertEquals(userId, result.getUserId())
            );
        }

        @ParameterizedTest(name = "#[{index}] Should throw exception [UserNotFoundException] for non existing " +
                "userId value: userId = {0}")
        @CsvFileSource(resources = "/csv/task/TaskTestInvalidEntries.csv", numLinesToSkip = 1)
        public void attemptToCreateInvalidTask(final Long userId) {
            final var task = TaskDTO.builder().userId(userId).build();

            when(userRepository.existsById(userId)).thenReturn(false);

            assertThrows(UserNotFoundException.class, () -> taskService.createTask(task));
        }

    }

    @Nested
    class ListTask {

        @ParameterizedTest(name = "#[{index}] Should assert equals for object provided as argument to repository for" +
                " values: userId = {0} | description = {1}")
        @CsvFileSource(resources = "/csv/task/TaskTestValidEntries.csv", numLinesToSkip = 1)
        public void findTaskByUserIdShouldReturnObject(final Long userId, final String description) {
            final var userIdArgCaptor = ArgumentCaptor.forClass(Long.class);

            when(taskRepository.findByUserId(userId)).thenReturn(buildTaskEntity(userId, description));

            final var result = taskService.findAllTasksForUserId(userId);

            verify(taskRepository).findByUserId(userIdArgCaptor.capture());

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(userId, userIdArgCaptor.getValue()),
                    () -> assertEquals(userId, result.get(0).getUserId()),
                    () -> assertEquals(description, result.get(0).getDescription())
            );
        }


        @ParameterizedTest(name = "#[{index}] Should return empty list for non existing " +
                "userId value: userId = {0}")
        @CsvFileSource(resources = "/csv/task/TaskTestInvalidEntries.csv", numLinesToSkip = 1)
        public void findTaskByUserIdShouldReturnEmpty(final Long userId) {
            final var userIdArgCaptor = ArgumentCaptor.forClass(Long.class);

            when(taskRepository.findByUserId(userId)).thenReturn(Collections.emptyList());

            final var result = taskService.findAllTasksForUserId(userId);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertTrue(result.isEmpty())
            );
        }
    }

    private List<TaskEntity> buildTaskEntity(final Long userId, final String description) {
        final var user = UserEntity.builder().build();
        user.setId(userId);

        return List.of(
                TaskEntity.builder()
                .description(description)
                .user(user)
                .build()
        );
    }
}
