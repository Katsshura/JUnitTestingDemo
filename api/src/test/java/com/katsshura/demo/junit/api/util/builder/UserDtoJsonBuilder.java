package com.katsshura.demo.junit.api.util.builder;

import com.github.javafaker.Faker;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDtoJsonBuilder implements BaseDtoJsonBuilder {

    private static final String SUFFIX_EMAIL_TEMPLATE = "??????####%s";

    private final Faker faker;

    public UserDtoJsonBuilder() {
        faker = new Faker();
    }

    @Override
    public JSONObject buildValidJsonObject() throws JSONException {
        final var randomEmail = faker.bothify(String.format(SUFFIX_EMAIL_TEMPLATE, "@gmail.com"));
        final var randomName = generateRandomNamesThatMayContainMiddleName();

        return buildObject(randomEmail, randomName);
    }

    @Override
    public JSONObject buildInvalidJsonObject() throws JSONException {
        final var randomEmail = faker.bothify(SUFFIX_EMAIL_TEMPLATE);
        final var randomName = faker.name().firstName();

        return buildObject(randomEmail, randomName);
    }

    private JSONObject buildObject(final String randomEmail, final String randomName) throws JSONException {
        final var user = new JSONObject();
        user.put("email", randomEmail);
        user.put("name", randomName);
        return user;
    }

    private String generateRandomNamesThatMayContainMiddleName() {
        final var randomNumber = faker.number().randomNumber();
        return (randomNumber % 2 == 0) ? faker.name().nameWithMiddle() : faker.name().name();
    }
}
