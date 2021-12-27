package com.katsshura.demo.junit.core.integration;

import com.katsshura.demo.junit.core.config.IntegrationTestsConfiguration;
import com.katsshura.demo.junit.core.entities.user.UserEntity;
import com.katsshura.demo.junit.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@IntegrationTestsConfiguration
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void contextLoads() {
        assertAll(
                () -> assertThat(this.userRepository, is(notNullValue()))
        );
    }

    @Nested
    class CreateUser {

        @ParameterizedTest(name = "[{index}] Should assert equals for parameters saved in database with values:" +
                " email = {0} | firstName = {1} | lastName = {2} | fullName = {3}")
        @CsvFileSource(resources = "/csv/UserTestValidEntries.csv", numLinesToSkip = 1)
        public void createValidUsers(final String email,
                                     final String firstName,
                                     final String lastName,
                                     final String fullName) {

            final var user = UserEntity.builder()
                    .email(email)
                    .firstName(firstName)
                    .lastName(lastName)
                    .fullName(fullName)
                    .build();

            final var result = userRepository.save(user);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(email, result.getEmail()),
                    () -> assertEquals(firstName, result.getFirstName()),
                    () -> assertEquals(lastName, result.getLastName()),
                    () -> assertEquals(fullName, result.getFullName())
            );
        }
    }

    @Nested
    class ListUser {

    }
}
