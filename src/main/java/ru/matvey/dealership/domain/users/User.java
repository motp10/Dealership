package ru.matvey.dealership.domain.users;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;

@Entity
@Table(name = "app_users")
@Getter
public class User {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "role")
    private String role;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "removed")
    private boolean removed = false;

    protected User() {}

    public User(
        String username,
        String password,
        String fullName,
        UserRole role
    ) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role.getValue();
    }

    public User(
        UUID id,
        String username,
        String password,
        String fullName,
        UserRole role
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role.getValue();
    }

    public UserRole getUserRole() {
        return UserRole.fromString(this.role);
    }

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
