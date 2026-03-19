package ru.matvey.dealership.infrastracture.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "app_users")
@Getter @Setter
public class UserEntity extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Column(name = "full_name")
    private String fullName;

    private String role;
}