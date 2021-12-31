package com.katsshura.demo.junit.api.controller;

import com.katsshura.demo.junit.api.config.ControllerTestsConfiguration;
import com.katsshura.demo.junit.api.util.builder.UserDtoJsonBuilder;
import com.katsshura.demo.junit.api.util.source.RandomJSONSource;
import com.katsshura.demo.junit.core.dto.user.UserDTO;
import com.katsshura.demo.junit.core.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTestsConfiguration
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static final String REQUEST_URL = "/user";

    @Nested
    @DisplayName("[POST] /user/")
    class CreateUser {

        @ParameterizedTest(name = "#[{index}] Should assert status code 201 for json value: {0}")
        @RandomJSONSource(interactions = 10, targetBuilder = UserDtoJsonBuilder.class)
        public void createValidUser(final String json, final UserDTO obj) throws Exception {

            when(userService.createUser(obj)).thenReturn(obj);

            mockMvc.perform(post(REQUEST_URL)
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isCreated())
                    .andExpect(header().exists("Location"));
        }

        @ParameterizedTest(name = "#[{index}] Should assert status code 400 for json value: {0}")
        @RandomJSONSource(interactions = 10, targetBuilder = UserDtoJsonBuilder.class, invalidFields = true)
        public void createInvalidUser(final String json) throws Exception {
            mockMvc.perform(post(REQUEST_URL)
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[GET] /user/")
    class GetUser {
        @ParameterizedTest(name = "#[{index}] Should assert status code 200 and return a valid UserDTO")
        @RandomJSONSource(interactions = 10, targetBuilder = UserDtoJsonBuilder.class)
        public void findUserByEmail(final String json, final UserDTO obj) throws Exception {

            when(userService.findUserByEmail(obj.getEmail())).thenReturn(obj);

            mockMvc.perform(get(REQUEST_URL+"/{email}", obj.getEmail()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value(obj.getName()))
                    .andExpect(jsonPath("$.email").value(obj.getEmail()));
        }

        @ParameterizedTest(name = "#[{index}] Should assert status code 204 for no valid UserDTO")
        @RandomJSONSource(interactions = 10, targetBuilder = UserDtoJsonBuilder.class)
        public void couldNotFindUserByEmail(final String json, final UserDTO obj) throws Exception {

            when(userService.findUserByEmail(obj.getEmail())).thenReturn(null);

            mockMvc.perform(get(REQUEST_URL+"/{email}", obj.getEmail()))
                    .andExpect(status().isNoContent());
        }
    }
}
