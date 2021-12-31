package com.katsshura.demo.junit.api.response;

import com.katsshura.demo.junit.api.model.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@Service
public class ResponseBuilder {

    public ResponseEntity<ResponseError> buildErrorResponse(final Exception cause, final HttpStatus statusCode) {
        final ResponseError response = ResponseError.builder().reason(cause).build();
        return new ResponseEntity<>(response, statusCode);
    }

    public <T> ResponseEntity<T> buildCreatedResponse(final Object responseObjectId) {

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseObjectId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    public <T> ResponseEntity<T> buildCollectionResponseOkOrNoContent(final T value) {
        if (value == null || ((Collection<?>) value).isEmpty()) {
            return buildResponseOkOrNoContent(null);
        }

        return buildResponseOkOrNoContent(value);
    }

    public  <T> ResponseEntity<T> buildResponseOkOrNoContent(final T value) {
        if (value == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(value);
    }
}
