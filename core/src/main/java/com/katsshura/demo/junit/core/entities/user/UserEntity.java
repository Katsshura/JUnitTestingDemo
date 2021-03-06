package com.katsshura.demo.junit.core.entities.user;

import com.katsshura.demo.junit.core.entities.BaseEntity;
import com.katsshura.demo.junit.core.entities.task.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "`user`")
public class UserEntity extends BaseEntity {
    private static final long serialVersionUID = 2415779894763455356L;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<TaskEntity> tasks;
}
