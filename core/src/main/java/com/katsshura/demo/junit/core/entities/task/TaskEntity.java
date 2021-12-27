package com.katsshura.demo.junit.core.entities.task;

import com.katsshura.demo.junit.core.entities.BaseEntity;
import com.katsshura.demo.junit.core.entities.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "task")
public class TaskEntity extends BaseEntity {
    private static final long serialVersionUID = -2468372313727870426L;

    private String description;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
}
