package ru.matvey.dealership.contracts.services;

import java.util.UUID;

public interface UserService {

    UUID addUser(String username, String password, String fullName, String role);

    void deleteUser(UUID id);
}
