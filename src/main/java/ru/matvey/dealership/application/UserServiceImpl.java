package ru.matvey.dealership.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.matvey.dealership.abstraction.UserRepository;
import ru.matvey.dealership.contracts.services.UserService;
import ru.matvey.dealership.domain.users.User;
import ru.matvey.dealership.domain.users.UserRole;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UUID addUser(String username, String password, String fullName, String role) {
        UserRole userRole = UserRole.fromString(role);
        User user = new User(username, password, fullName, userRole);
        userRepository.addUser(user);
        return user.getId();
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.delete(id);
    }
}
