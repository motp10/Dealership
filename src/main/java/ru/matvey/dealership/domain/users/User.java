package ru.matvey.dealership.domain.users;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class User {
    public User(UUID id, String username, String password, String fullName, UserRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    public User(String username, String password, String fullName, UserRole role) {
        id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    private final UUID id;
    private final String username;
    private final String password;
    private final String fullName;
    private final UserRole role;
}
