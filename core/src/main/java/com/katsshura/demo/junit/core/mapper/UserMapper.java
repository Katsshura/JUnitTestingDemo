package com.katsshura.demo.junit.core.mapper;

import com.katsshura.demo.junit.core.dto.user.UserDTO;
import com.katsshura.demo.junit.core.entities.user.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserDTO toDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        return UserDTO
                .builder()
                .email(userEntity.getEmail())
                .name(userEntity.getFullName())
                .build();
    }

    public UserEntity toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        return UserEntity
                .builder()
                .email(userDTO.getEmail())
                .fullName(userDTO.getName())
                .build();
    }
}
