package com.katsshura.demo.junit.api.controller;

import com.katsshura.demo.junit.api.response.ResponseBuilder;
import com.katsshura.demo.junit.core.dto.user.UserDTO;
import com.katsshura.demo.junit.core.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ResponseBuilder responseBuilder;

    public UserController(UserService userService, ResponseBuilder responseBuilder) {
        this.userService = userService;
        this.responseBuilder = responseBuilder;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody final UserDTO userDTO) {
        log.info("Attempting to create user with info: {}", userDTO);
        final var result = userService.createUser(userDTO);
        return responseBuilder.buildCreatedResponse(result.getEmail());
    }

    @GetMapping(path = "/{userEmail}")
    public ResponseEntity<UserDTO> findUserByEmail(@PathVariable final String userEmail) {
        log.info("Attempting to find user with email: {}", userEmail);
        final var result = userService.findUserByEmail(userEmail);
        return responseBuilder.buildResponseOkOrNoContent(result);
    }
}
