package ru.matvey.dealership.presentation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.matvey.dealership.contracts.models.ConfigurationOrderDto;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.services.OrderService;
import ru.matvey.dealership.presentation.models.CarConfigurationRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/configurationCars")
@RequiredArgsConstructor
public class ConfigurationCarOrderController {
    OrderService orderService;
    @PostMapping
    public UUID makeOrder(@RequestBody CarConfigurationRequest request, JwtAuthenticationToken auth) {
        UUID clientId = UUID.fromString(auth.getToken().getSubject());
        CarComplictation complictation = new CarComplictation(0,
                request.brand(),
                request.model(),
                request.bodyType(),
                request.fuel(),
                request.transmission(),
                request.driveLayout(),
                request.paint(),
                request.wheel());
        return orderService.makeOrderWithConfiguration(complictation, clientId);
    }
    @PatchMapping("/update")
    public void updateOrderStatus(@RequestBody UUID id) {
        orderService.updateOrderStatus(id);
    }
    @PatchMapping("/cancel")
    public void cancelOrder(@RequestBody UUID id, JwtAuthenticationToken auth) {
        UUID clientId = UUID.fromString(auth.getToken().getSubject());
        orderService.cancelOrder(id, clientId);
    }
    @GetMapping("/{id}")
    public ConfigurationOrderDto getOrder(@RequestBody UUID id, JwtAuthenticationToken auth) {
        UUID clientId = UUID.fromString(auth.getToken().getSubject());
        return orderService.getConfigurationOrder(id, clientId);
    }
    @GetMapping
    public List<ConfigurationOrderDto> getAllOrders() {
        return orderService.getAllConfigurationOrders();
    }
}