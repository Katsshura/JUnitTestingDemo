package com.katsshura.demo.junit.api.handler;

import com.katsshura.demo.junit.api.response.ResponseBuilder;
import com.katsshura.demo.junit.api.model.ResponseError;
import com.katsshura.demo.junit.core.exceptions.InvalidNameException;
import com.katsshura.demo.junit.core.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@Controller
public class ExceptionHandlerConfig {

    private final ResponseBuilder responseBuilder;

    public ExceptionHandlerConfig(ResponseBuilder responseBuilder) {
        this.responseBuilder = responseBuilder;
    }

    @ExceptionHandler(InvalidNameException.class)
    public final ResponseEntity<ResponseError> handleInvalidNameException(final InvalidNameException e) {
        return responseBuilder.buildErrorResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ResponseError> handleUserNotFoundException(final UserNotFoundException e) {
        return responseBuilder.buildErrorResponse(e, HttpStatus.BAD_REQUEST);
    }
}
