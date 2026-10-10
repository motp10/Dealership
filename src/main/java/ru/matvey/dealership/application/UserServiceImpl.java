
package ru.matvey.dealership.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.matvey.dealership.abstraction.UserRepository;
import ru.matvey.dealership.contracts.services.UserService;
import ru.matvey.dealership.domain.users.User;
import ru.matvey.dealership.domain.users.UserRole;

import java.util.UUID;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UUID addUser(
            String username,
            String password,
            String fullName,
            String role
    ) {
        User user;

        try {
            UserRole userRole = UserRole.fromString(role);
            user = new User(username, password, fullName, userRole);
        } catch (RuntimeException exception) {
            log.warn(
                    "Failed to construct user",
                    kv("username", username),
                    kv("role", role),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        }

        userRepository.addUser(user);

        log.info(
                "User created successfully",
                kv("userId", user.getId()),
                kv("role", role)
        );

        return user.getId();
    }

    @Override
    public void deleteUser(UUID id) {
        try {
            userRepository.delete(id);

            log.info(
                    "User deleted successfully",
                    kv("userId", id)
            );
        } catch (RuntimeException exception) {
            log.error(
                    "Failed to delete user",
                    kv("userId", id),
                    kv("errorType", exception.getClass().getSimpleName()),
                    exception
            );
            throw exception;
        }
    }
}
