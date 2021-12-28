package com.katsshura.demo.junit.core.integration;

import com.katsshura.demo.junit.core.config.IntegrationTestsConfiguration;
import com.katsshura.demo.junit.core.entities.task.TaskEntity;
import com.katsshura.demo.junit.core.entities.user.UserEntity;
import com.katsshura.demo.junit.core.repository.TaskRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void contextLoads() {
        assertAll(
                () -> assertThat(this.taskRepository, is(notNullValue()))
        );
    }

    @Nested
    @IntegrationTestsConfiguration
    class CreateTask {

        @ParameterizedTest(name = "#[{index}] Should assert equals for parameters saved in database with values:" +
                " userId = {0} | description = {1}")
        @CsvFileSource(resources = "/csv/task/TaskTestValidEntries.csv", numLinesToSkip = 1)
        @SqlGroup({
                @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                        scripts = { "classpath:scripts/user/BeforeUserRepositoryTest.sql"}),
                @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                        scripts = { "classpath:scripts/user/AfterUserRepositoryTest.sql" })
        })
        public void createValidTask(final Long userId, final String description) {
            final var user = new UserEntity();
            user.setId(userId);
            final var task = TaskEntity.builder().description(description).user(user).build();

            final var result = taskRepository.save(task);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertNotNull(result.getUser()),
                    () -> assertEquals(description, result.getDescription()),
                    () -> assertEquals(userId, result.getUser().getId())
            );
        }

        @ParameterizedTest(name = "#[{index}] Should throw exception [DataIntegrityViolationException] for invalid " +
                "parameters values: userId = {0} | description = {1}")
        @CsvFileSource(resources = "/csv/task/TaskTestInvalidEntries.csv", numLinesToSkip = 1)
        @SqlGroup({
                @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                        scripts = { "classpath:scripts/user/BeforeUserRepositoryTest.sql"}),
                @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                        scripts = { "classpath:scripts/user/AfterUserRepositoryTest.sql" })
        })
        public void createInvalidTask(final Long userId, final String description) {
            final var user = new UserEntity();
            user.setId(userId);
            final var task = TaskEntity.builder().description(description).user(user).build();

            assertThrows(DataIntegrityViolationException.class, () -> taskRepository.save(task));
        }
    }

    @Nested
    @IntegrationTestsConfiguration
    class ListTask {

    }

}
