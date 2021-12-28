package com.katsshura.demo.junit.core.service;

import com.github.javafaker.Faker;
import com.katsshura.demo.junit.core.dto.user.UserDTO;
import com.katsshura.demo.junit.core.entities.user.UserEntity;
import com.katsshura.demo.junit.core.exceptions.InvalidNameException;
import com.katsshura.demo.junit.core.mapper.UserMapper;
import com.katsshura.demo.junit.core.repository.UserRepository;
import com.katsshura.demo.junit.core.service.user.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private final Faker faker = new Faker();

    @Nested
    class CreateUser {

        @ParameterizedTest(name = "#[{index}] Should assert equals for expected values based on name: {1}" +
                " email = {0} | expectedFirstName = {2}| expectedLastName = {3} | expectedFullName = {4}")
        @CsvFileSource(resources = "/csv/user/UserServiceTestValidEntries.csv", numLinesToSkip = 1)
        public void validateCreationWithValidParameters(final String email,
                                                        final String name,
                                                        final String expectedFirstName,
                                                        final String expectedLastName,
                                                        final String expectedFullName) {

            final var userDto = UserDTO.builder().email(email).name(name).build();
            final var userArgumentCaptor = ArgumentCaptor.forClass(UserEntity.class);

            when(userRepository.save(any())).thenReturn(buildMockRandomEntity(null));

            final var result = userService.createUser(userDto);

            verify(userRepository).save(userArgumentCaptor.capture());

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(expectedFirstName, userArgumentCaptor.getValue().getFirstName()),
                    () -> assertEquals(expectedLastName, userArgumentCaptor.getValue().getLastName()),
                    () -> assertEquals(expectedFullName, userArgumentCaptor.getValue().getFullName())
            );

        }

        @ParameterizedTest(name = "#[{index}] Should throws exception [InvalidNameException] for invalid name values:" +
                " email = {0} | name = {1}")
        @CsvFileSource(resources = "/csv/user/UserServiceTestInvalidEntries.csv", numLinesToSkip = 1)
        public void validateCreationWithInvalidParameters(final String email, final String name) {
            final var userDto = UserDTO.builder().email(email).name(name).build();

            assertThrows(InvalidNameException.class, () -> userService.createUser(userDto));
        }
    }

    @Nested
    class ListUser {

        @ParameterizedTest(name = "#[{index}] Should call repository with entity and its email value must match " +
                "email = {0} and should return not null")
        @CsvFileSource(resources = "/csv/user/UserServiceTestValidEntries.csv", numLinesToSkip = 1)
        public void shouldReturnNotNull(final String email) {
            final var emailArgumentCaptor = ArgumentCaptor.forClass(String.class);

            when(userRepository.findByEmail(email)).thenReturn(Optional.of(buildMockRandomEntity(email)));

            final var result = userService.findUserByEmail(email);

            verify(userRepository).findByEmail(emailArgumentCaptor.capture());

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(email, emailArgumentCaptor.getValue())
            );
        }
    }

    private UserEntity buildMockRandomEntity(final String email) {
        final var name = faker.name();
        return UserEntity.builder()
                .email(faker.bothify(email == null ? "???????#####@gmail.com" : email))
                .firstName(name.firstName())
                .lastName(name.lastName())
                .fullName(name.fullName())
                .build();
    }

}