package com.katsshura.demo.junit.api.util.builder;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class TaskDtoJsonBuilder implements BaseDtoJsonBuilder {

    private final Faker faker;

    public TaskDtoJsonBuilder() {
        faker = new Faker();
    }

    @Override
    public JSONObject buildValidJsonObject() throws JSONException {
        final var randomPositiveUserId = faker.number().numberBetween(1L, Long.MAX_VALUE/2);
        final var randomParagraphs = faker.number().randomDigitNotZero();
        final var randomDescription = faker.lorem().paragraphs(randomParagraphs);

        return buildObject(randomPositiveUserId, String.join( "\n", randomDescription));
    }

    @Override
    public JSONObject buildInvalidJsonObject() throws JSONException {
        final var randomPositiveUserId = faker.number().numberBetween(Long.MIN_VALUE/2, 0);
        final var randomDescription = generateInvalidDescription();

        return buildObject(randomPositiveUserId, String.join( "\n", randomDescription));
    }

    private String generateInvalidDescription() {
        final var randomNumber = faker.number().randomNumber();
        return (randomNumber % 2 == 0) ? null : StringUtils.EMPTY;
    }

    private JSONObject buildObject(final Long randomUserId, final String description) throws JSONException {
        final var task = new JSONObject();
        task.put("userId", randomUserId);
        task.put("description", description);
        return task;
    }


}
