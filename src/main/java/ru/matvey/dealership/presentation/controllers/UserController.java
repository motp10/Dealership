package ru.matvey.dealership.presentation.controllers;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;
import ru.matvey.dealership.contracts.services.UserService;
import ru.matvey.dealership.presentation.models.UserRequest;

import java.util.UUID;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MeterRegistry meterRegistry;

    @PostConstruct
    void registerBusinessMetrics() {
        meterRegistry.counter("dealership_users_created_total");
        meterRegistry.counter("dealership_users_deleted_total");
    }

    @PostMapping
    public UUID addUser(@RequestBody UserRequest request) {
        UUID userId = userService.addUser(
            request.name(),
            request.password(),
            request.FullName(),
            request.Role()
        );
        meterRegistry.counter("dealership_users_created_total").increment();
        log.info("User creation request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "UserController"),
            kv("operation", "create"),
            kv("userId", userId),
            kv("role", request.Role()));
        return userId;
    }

    @DeleteMapping
    public void deleteUser(@RequestBody UUID id) {
        userService.deleteUser(id);
        meterRegistry.counter("dealership_users_deleted_total").increment();
        log.info("User deletion request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "UserController"),
            kv("operation", "delete"),
            kv("userId", id));
    }
}
