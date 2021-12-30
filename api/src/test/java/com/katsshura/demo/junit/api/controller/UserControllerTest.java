package com.katsshura.demo.junit.api.controller;

import com.katsshura.demo.junit.api.config.ControllerTestsConfiguration;
import com.katsshura.demo.junit.api.util.source.RandomJSONSource;
import com.katsshura.demo.junit.core.dto.user.UserDTO;
import com.katsshura.demo.junit.core.service.user.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ControllerTestsConfiguration
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    private static final String REQUEST_URL = "/user";

    @Nested
    class CreateUser {

        @ParameterizedTest
        @RandomJSONSource(interactions = 10, target = UserDTO.class)
        public void testResource(final String json) {
            assertNotNull(json);
        }
    }

    @Nested
    class GetUser {

    }
}
