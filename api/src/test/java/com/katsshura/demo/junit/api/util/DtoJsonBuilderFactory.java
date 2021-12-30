package com.katsshura.demo.junit.api.util;

import com.katsshura.demo.junit.api.util.builder.BaseDtoJsonBuilder;
import com.katsshura.demo.junit.api.util.builder.TaskDtoJsonBuilder;
import com.katsshura.demo.junit.api.util.builder.UserDtoJsonBuilder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Component
public class DtoJsonBuilderFactory {

    private static final Collection<BaseDtoJsonBuilder> buildersInstance = createInstances();

    public static Optional<BaseDtoJsonBuilder> getBuilder(Class<?> type) {
        return buildersInstance.stream().filter(builder -> builder.getClass().equals(type)).findFirst();
    }

    private static Collection<BaseDtoJsonBuilder> createInstances() {
        return Arrays.asList(
                new TaskDtoJsonBuilder(),
                new UserDtoJsonBuilder()
        );
    }

}