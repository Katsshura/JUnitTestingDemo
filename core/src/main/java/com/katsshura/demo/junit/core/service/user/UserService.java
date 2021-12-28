package com.katsshura.demo.junit.core.service.user;

import com.katsshura.demo.junit.core.dto.user.UserDTO;
import com.katsshura.demo.junit.core.entities.user.UserEntity;
import com.katsshura.demo.junit.core.exceptions.InvalidNameException;
import com.katsshura.demo.junit.core.mapper.UserMapper;
import com.katsshura.demo.junit.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO createUser(final UserDTO userDTO) {
        var name = userDTO.getName().trim().toLowerCase();

        if (!name.contains(" ")) {
            throw new InvalidNameException(name);
        }

        userDTO.setName(name);

        final var userEntity = userMapper.toEntity(userDTO);

        extractFirstAndLastNameToEntity(userEntity);

        final var result = userRepository.save(userEntity);

        return userMapper.toDto(result);
    }

    private void extractFirstAndLastNameToEntity(UserEntity userEntity) {
        final String name = userEntity.getFullName();

        final var splitStr = name.split(" ");
        final var firstName = splitStr[0];
        final var lastName = splitStr[splitStr.length - 1];

        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
    }
}
