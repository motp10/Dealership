package ru.matvey.dealership.presentation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.matvey.dealership.contracts.services.UserService;
import ru.matvey.dealership.presentation.models.UserRequest;

import java.util.UUID;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public UUID addUser(@RequestBody UserRequest request) {
        return userService.addUser(request.name(), request.password(), request.FullName(), request.Role());
    }

    @DeleteMapping
    public void deleteUser(@RequestBody UUID id) {
        userService.deleteUser(id);
    }
}
