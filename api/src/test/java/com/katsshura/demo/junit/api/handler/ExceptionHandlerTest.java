package com.katsshura.demo.junit.api.handler;

import com.katsshura.demo.junit.api.response.ResponseBuilder;
import com.katsshura.demo.junit.core.exceptions.InvalidNameException;
import com.katsshura.demo.junit.core.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ExceptionHandlerTest {

    @Spy
    private ResponseBuilder responseBuilder;

    @InjectMocks
    private ExceptionHandlerConfig handlerConfig;

    @Nested
    class BadRequestExpected {

        @ParameterizedTest(name = "#[{index}] Should return status code 400 for InvalidNameException!")
        @ValueSource(strings = {"Name One", "Name Two", "Name Three", "Name Four"})
        public void shouldReturnBadRequestForInvalidNameException(final String name) {
            final var exception = new InvalidNameException(name);
            final var response = handlerConfig.handleInvalidNameException(exception);

            assertAll(
                    () -> assertNotNull(response),
                    () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode())
            );
        }

        @ParameterizedTest(name = "#[{index}] Should return status code 400 for UserNotFoundException!")
        @ValueSource(longs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
        public void shouldReturnBadRequestForUserNotFoundException(final Long id) {
            final var exception = new UserNotFoundException(id);
            final var response = handlerConfig.handleUserNotFoundException(exception);

            assertAll(
                    () -> assertNotNull(response),
                    () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode())
            );
        }

    }

}
