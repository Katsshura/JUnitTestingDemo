package com.katsshura.demo.junit.core.repository;

import com.katsshura.demo.junit.core.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
