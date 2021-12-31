package com.katsshura.demo.junit.api.util.builder;

import com.github.javafaker.Faker;
import com.katsshura.demo.junit.core.dto.user.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserDtoJsonBuilder implements BaseDtoJsonBuilder<UserDTO> {

    private static final String PREFIX_EMAIL_TEMPLATE = "??????####%s";

    private final Faker faker;

    public UserDtoJsonBuilder() {
        faker = new Faker();
    }

    @Override
    public Map<UserDTO, JSONObject> buildValidJsonObject() throws JSONException {
        final var randomEmail = faker.bothify(String.format(PREFIX_EMAIL_TEMPLATE, "@gmail.com"));
        final var randomName = generateRandomNamesThatMayContainMiddleName();

        return buildObject(randomEmail, randomName);
    }

    @Override
    public Map<UserDTO, JSONObject> buildInvalidJsonObject() throws JSONException {
        final var randomEmail = faker.bothify(String.format(PREFIX_EMAIL_TEMPLATE, StringUtils.EMPTY));
        final var randomName = generateRandomInvalidAndValidNames();

        return buildObject(randomEmail, randomName);
    }

    private Map<UserDTO, JSONObject> buildObject(final String randomEmail, final String randomName) throws JSONException {
        final var user = new JSONObject();
        user.put("email", randomEmail);
        user.put("name", randomName);

        return Map.of(
                UserDTO.builder().name(randomName).email(randomEmail).build(),
                user
        );
    }

    private String generateRandomNamesThatMayContainMiddleName() {
        final var randomNumber = faker.number().randomNumber();
        return (randomNumber % 2 == 0) ? faker.name().nameWithMiddle() : faker.name().name();
    }

    private String generateRandomInvalidAndValidNames() {
        final var randomNumber = faker.number().randomNumber();
        return (randomNumber % 2 == 0) ? faker.name().firstName() : this.generateRandomNamesThatMayContainMiddleName();
    }
}
