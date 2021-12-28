package com.katsshura.demo.junit.core.integration;

import com.katsshura.demo.junit.core.config.IntegrationTestsConfiguration;
import com.katsshura.demo.junit.core.entities.user.UserEntity;
import com.katsshura.demo.junit.core.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

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
    @IntegrationTestsConfiguration
    class CreateUser {

        @ParameterizedTest(name = "#[{index}] Should assert equals for parameters saved in database with values:" +
                " email = {0} | firstName = {1} | lastName = {2} | fullName = {3}")
        @CsvFileSource(resources = "/csv/user/UserTestValidEntries.csv", numLinesToSkip = 1)
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

        @ParameterizedTest(name = "#[{index}] Should throw exception [DataIntegrityViolationException] for invalid " +
                "parameters values: email = {0} | firstName = {1} | lastName = {2} | fullName = {3}")
        @CsvFileSource(resources = "/csv/user/UserTestInvalidEntries.csv", numLinesToSkip = 1)
        @SqlGroup({
                @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                        scripts = "classpath:scripts/user/BeforeUserRepositoryTest.sql"),
                @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                        scripts = "classpath:scripts/user/AfterUserRepositoryTest.sql")
        })
        public void createInvalidUsers(final String email,
                                     final String firstName,
                                     final String lastName,
                                     final String fullName) {

            final var user = UserEntity.builder()
                    .email(email)
                    .firstName(firstName)
                    .lastName(lastName)
                    .fullName(fullName)
                    .build();

            assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user));
        }
    }

    @Nested
    @IntegrationTestsConfiguration
    class ListUser {

        @Test
        @DisplayName("Should assertNotNull and Equals list size 6 when retrieving all entries from DB")
        @SqlGroup({
                @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                        scripts = "classpath:scripts/user/BeforeUserRepositoryTest.sql"),
                @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                        scripts = "classpath:scripts/user/AfterUserRepositoryTest.sql")
        })
        public void listAllUsersInDatabase() {
            final int expectedListSize = 6;

            final var result = userRepository.findAll();

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(expectedListSize, result.size())
            );
        }

        @ParameterizedTest(name = "#[{index}] Should assertNotNull and assertEquals for all entity properties when" +
                " find by email, parameters values: email = {0} | firstName = {1} | lastName = {2} | fullName = {3}")
        @CsvFileSource(resources = "/csv/user/UserTestValidEntries.csv", numLinesToSkip = 1)
        @SqlGroup({
                @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                        scripts = "classpath:scripts/user/BeforeUserRepositoryTest.sql"),
                @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                        scripts = "classpath:scripts/user/AfterUserRepositoryTest.sql")
        })
        public void findUserByEmail(final String email,
                                    final String firstName,
                                    final String lastName,
                                    final String fullName) {

            final var result = userRepository.findByEmail(email);

            assertAll(
                    () -> assertTrue(result.isPresent()),
                    () -> assertEquals(email, result.get().getEmail()),
                    () -> assertEquals(firstName, result.get().getFirstName()),
                    () -> assertEquals(lastName, result.get().getLastName()),
                    () -> assertEquals(fullName, result.get().getFullName())
            );
        }

        @ParameterizedTest(name = "#[{index}] Should assertNotNull and assertEquals for all entity properties when" +
                " find by id, parameters values: email = {0} | firstName = {1} | lastName = {2} | fullName = {3}" +
                " | id = {4}")
        @CsvFileSource(resources = "/csv/user/UserTestValidEntries.csv", numLinesToSkip = 1)
        @SqlGroup({
                @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                        scripts = "classpath:scripts/user/BeforeUserRepositoryTest.sql"),
                @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                        scripts = "classpath:scripts/user/AfterUserRepositoryTest.sql")
        })
        public void findUserById(final String email,
                                 final String firstName,
                                 final String lastName,
                                 final String fullName,
                                 final Long id) {

            final var result = userRepository.findById(id);

            assertAll(
                    () -> assertTrue(result.isPresent()),
                    () -> assertEquals(email, result.get().getEmail()),
                    () -> assertEquals(firstName, result.get().getFirstName()),
                    () -> assertEquals(lastName, result.get().getLastName()),
                    () -> assertEquals(fullName, result.get().getFullName())
            );

        }

    }
}
