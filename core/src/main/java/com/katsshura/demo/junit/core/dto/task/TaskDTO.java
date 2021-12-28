package com.katsshura.demo.junit.core.dto.task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDTO {

    @NotNull
    @Positive
    @JsonProperty("userId")
    private Long userId;

    @NotBlank
    @JsonProperty("description")
    private String description;

}
