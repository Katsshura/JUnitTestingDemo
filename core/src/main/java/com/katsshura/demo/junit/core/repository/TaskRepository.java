package com.katsshura.demo.junit.core.repository;

import com.katsshura.demo.junit.core.entities.task.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
