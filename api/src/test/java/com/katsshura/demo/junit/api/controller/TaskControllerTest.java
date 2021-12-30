package com.katsshura.demo.junit.api.controller;

import com.katsshura.demo.junit.api.config.ControllerTestsConfiguration;
import com.katsshura.demo.junit.core.service.task.TaskService;
import org.junit.jupiter.api.Nested;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@ControllerTestsConfiguration
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    private static final String REQUEST_URL = "/user/task";

    @Nested
    class CreateTask {

    }

    @Nested
    class GetTask {

    }
}
