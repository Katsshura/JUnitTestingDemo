package com.katsshura.demo.junit.api.util.source;

import com.katsshura.demo.junit.api.util.builder.BaseDtoJsonBuilder;
import org.apiguardian.api.API;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@API(status = EXPERIMENTAL, since = "5.0")
@ArgumentsSource(RandomDTOJsonProvider.class)
public @interface RandomJSONSource {

    /**
     * Number of objects that will be generated to be provided to {@link ParameterizedTest}.
     */
    long interactions();

    /**
     * Class that implements {@link BaseDtoJsonBuilder}, will be used to generate de Object
     */
    Class<? extends BaseDtoJsonBuilder> targetBuilder();

    /**
     * Flag to produce invalid entries based on {@link BaseDtoJsonBuilder#buildInvalidJsonObject()}.
     */
    boolean invalidFields() default false;
}
