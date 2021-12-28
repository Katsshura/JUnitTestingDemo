package com.katsshura.demo.junit.core.mapper;

import com.katsshura.demo.junit.core.dto.user.UserDTO;
import com.katsshura.demo.junit.core.entities.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "userEntity.email", target = "email")
    @Mapping(source = "userEntity.fullName", target = "name")
    UserDTO toDto(UserEntity userEntity);

    @Mapping(source = "userDTO.email", target = "email")
    @Mapping(source = "userDTO.name", target = "fullName")
    UserEntity toEntity(UserDTO userDTO);
}
