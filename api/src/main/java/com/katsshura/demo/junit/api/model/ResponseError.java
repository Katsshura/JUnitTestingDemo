package com.katsshura.demo.junit.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseError {
    @JsonIgnoreProperties(value = {"stackTrace", "cause", "suppressed", "localizedMessage"}, allowSetters = true)
    @JsonProperty("errorReason")
    private final Exception reason;
}
