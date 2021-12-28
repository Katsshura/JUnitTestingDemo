package com.katsshura.demo.junit.core.repository;

import com.katsshura.demo.junit.core.entities.task.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query(value = "SELECT t FROM TaskEntity t WHERE t.user.id = :userId")
    List<TaskEntity> findByUserId(@Param("userId") Long userId);
}
