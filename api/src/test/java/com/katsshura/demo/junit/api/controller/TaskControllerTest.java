package com.katsshura.demo.junit.api.controller;

import com.katsshura.demo.junit.api.config.ControllerTestsConfiguration;
import com.katsshura.demo.junit.api.util.builder.TaskDtoJsonBuilder;
import com.katsshura.demo.junit.api.util.source.RandomJSONSource;
import com.katsshura.demo.junit.core.dto.task.TaskDTO;
import com.katsshura.demo.junit.core.service.task.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ControllerTestsConfiguration
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private static final String REQUEST_URL = "/user/task";

    @Nested
    @DisplayName("[POST] /user/task")
    class CreateTask {

        @ParameterizedTest(name = "#[{index}] Should assert status code 201 for json value: {0}")
        @RandomJSONSource(interactions = 10, targetBuilder = TaskDtoJsonBuilder.class)
        public void createValidTask(final String json, final TaskDTO obj) throws Exception {

            when(taskService.createTask(any())).thenReturn(obj);

            mockMvc.perform(post(REQUEST_URL)
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isCreated())
                    .andExpect(header().exists("Location"));

        }

        @ParameterizedTest(name = "#[{index}] Should assert status code 400 for invalid json value: {0}")
        @RandomJSONSource(interactions = 10, targetBuilder = TaskDtoJsonBuilder.class, invalidFields = true)
        public void createInvalidTask(final String json) throws Exception {
            mockMvc.perform(post(REQUEST_URL)
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[GET] /user/task")
    class GetTask {

        @ParameterizedTest(name = "#[{index}] Should assert status code 200 and return a valid List of TaskDTO")
        @RandomJSONSource(interactions = 10, targetBuilder = TaskDtoJsonBuilder.class)
        public void findAllByUserIdReturnList(final String json, final TaskDTO obj) throws Exception {

            when(taskService.findAllTasksForUserId(obj.getUserId())).thenReturn(List.of(obj));

            mockMvc.perform(get(REQUEST_URL).param("userId", obj.getUserId().toString()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].description").value(obj.getDescription()))
                    .andExpect(jsonPath("$[0].userId").value(obj.getUserId()));

        }

        @ParameterizedTest(name = "#[{index}] Should assert status code 204 and return a empty List of TaskDTO")
        @RandomJSONSource(interactions = 10, targetBuilder = TaskDtoJsonBuilder.class)
        public void findAllByUserIdReturnEmptyList(final String json, final TaskDTO obj) throws Exception {

            when(taskService.findAllTasksForUserId(obj.getUserId())).thenReturn(Collections.emptyList());

            mockMvc.perform(get(REQUEST_URL).param("userId", obj.getUserId().toString()))
                    .andExpect(status().isNoContent());

        }

        @ParameterizedTest(name = "#[{index}] Should assert status code 200 and return a valid TaskDTO")
        @RandomJSONSource(interactions = 10, targetBuilder = TaskDtoJsonBuilder.class)
        public void findByIdNotNull(final String json, final TaskDTO obj) throws Exception {

            when(taskService.findTaskById(obj.getId())).thenReturn(obj);

            mockMvc.perform(get(REQUEST_URL+"/{id}", obj.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.description").value(obj.getDescription()))
                    .andExpect(jsonPath("$.userId").value(obj.getUserId()));

        }

        @ParameterizedTest(name = "#[{index}] Should assert status code 204 and return a null TaskDTO")
        @RandomJSONSource(interactions = 10, targetBuilder = TaskDtoJsonBuilder.class)
        public void findByIdNull(final String json, final TaskDTO obj) throws Exception {

            when(taskService.findTaskById(obj.getId())).thenReturn(null);

            mockMvc.perform(get(REQUEST_URL).param("userId", obj.getUserId().toString()))
                    .andExpect(status().isNoContent());

        }

    }
}
