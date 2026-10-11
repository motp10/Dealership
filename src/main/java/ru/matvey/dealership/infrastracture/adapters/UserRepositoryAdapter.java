package ru.matvey.dealership.infrastracture.adapters;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.abstraction.UserRepository;
import ru.matvey.dealership.domain.users.User;
import ru.matvey.dealership.infrastracture.jparepositories.JpaUserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public void addUser(User user) {
        try {
            jpaUserRepository.save(user);
        } catch (RuntimeException exception) {
            log.error("Failed to save user",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "UserRepositoryAdapter"),
                    kv("operation", "save"),
                    kv("userId", user.getId()),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
        log.info("User saved",
                kv("component", "repository-adapter"),
                kv("adapter_name", "UserRepositoryAdapter"),
                kv("operation", "save"),
                kv("userId", user.getId()),
                kv("role", user.getRole()));
    }

    @Override
    public Optional<User> findById(UUID id) {
        try {
            Optional<User> user = jpaUserRepository.findById(id);
            log.debug("User lookup completed",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "UserRepositoryAdapter"),
                    kv("operation", "findById"),
                    kv("userId", id),
                    kv("found", user.isPresent()));
            return user;
        } catch (RuntimeException exception) {
            log.error("Failed to find user",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "UserRepositoryAdapter"),
                    kv("operation", "findById"),
                    kv("userId", id),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            jpaUserRepository.deleteById(id);
        } catch (RuntimeException exception) {
            log.error("Failed to delete user",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "UserRepositoryAdapter"),
                    kv("operation", "delete"),
                    kv("userId", id),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
        log.info("User deleted",
                kv("component", "repository-adapter"),
                kv("adapter_name", "UserRepositoryAdapter"),
                kv("operation", "delete"),
                kv("userId", id));
    }

    @Override
    public List<User> getAll() {
        try {
            List<User> users = jpaUserRepository.findAll();
            log.debug("Users retrieved",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "UserRepositoryAdapter"),
                    kv("operation", "findAll"),
                    kv("count", users.size()));
            return users;
        } catch (RuntimeException exception) {
            log.error("Failed to retrieve users",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "UserRepositoryAdapter"),
                    kv("operation", "findAll"),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
    }

    @Override
    public List<User> findByRole(String role) {
        try {
            List<User> users = jpaUserRepository.findByRole(role);
            log.debug("Users retrieved by role",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "UserRepositoryAdapter"),
                    kv("operation", "findByRole"),
                    kv("role", role),
                    kv("count", users.size()));
            return users;
        } catch (RuntimeException exception) {
            log.error("Failed to retrieve users by role",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "UserRepositoryAdapter"),
                    kv("operation", "findByRole"),
                    kv("role", role),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
    }

    @Override
    public UUID getRandomManager() {
        List<User> managers;
        try {
            managers = jpaUserRepository.findByRole("MANAGER");
        } catch (RuntimeException exception) {
            log.error("Failed to retrieve managers",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "UserRepositoryAdapter"),
                    kv("operation", "getRandomManager"),
                    kv("role", "MANAGER"),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception);
            throw exception;
        }
        if (managers.isEmpty()) {
            log.warn("No managers available",
                    kv("component", "repository-adapter"),
                    kv("adapter_name", "UserRepositoryAdapter"),
                    kv("operation", "getRandomManager"),
                    kv("role", "MANAGER"),
                    kv("count", 0));
            throw new IllegalStateException("No managers available in the database");
        }
        int randomIndex = new Random().nextInt(managers.size());
        UUID managerId = managers.get(randomIndex).getId();
        log.debug("Manager selected",
                kv("component", "repository-adapter"),
                kv("adapter_name", "UserRepositoryAdapter"),
                kv("operation", "getRandomManager"),
                kv("role", "MANAGER"),
                kv("managerCount", managers.size()));
        return managerId;
    }
}