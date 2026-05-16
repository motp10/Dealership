package ru.matvey.dealership.abstraction;

import ru.matvey.dealership.domain.users.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void addUser(User user);

    Optional<User> findById(UUID id);

    void delete(UUID id);

    List<User> getAll();

    List<User> findByRole(String role);

    UUID getRandomManager();
}