package ru.matvey.dealership.presentation.controllers;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.matvey.dealership.contracts.models.ConfigurationOrderDto;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.services.OrderService;
import ru.matvey.dealership.contracts.services.UserService;
import ru.matvey.dealership.presentation.models.CarConfigurationRequest;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/configurationCars")
@RequiredArgsConstructor
public class ConfigurationCarOrderController {
    OrderService orderService;
    @PostMapping
    public UUID makeOrder(@RequestBody CarConfigurationRequest request) {
        CarComplictation complictation = new CarComplictation(0,
                request.brand(),
                request.model(),
                request.bodyType(),
                request.fuel(),
                request.transmission(),
                request.driveLayout(),
                request.paint(),
                request.wheel());
        return orderService.makeOrderWithConfiguration(complictation, request.clientId());
    }
    @PatchMapping("/update")
    public void updateOrderStatus(@RequestBody UUID id) {
        orderService.updateOrderStatus(id);
    }
    @PatchMapping("/cancel")
    public void cancelOrder(@RequestBody UUID id) {
        orderService.cancelOrder(id);
    }
    @GetMapping("/{id}")
    public ConfigurationOrderDto getOrder(@RequestBody UUID id) {
        return orderService.getConfigurationOrder(id);
    }
    @GetMapping
    public List<ConfigurationOrderDto> getAllOrders() {
        return orderService.getAllConfigurationOrders();
    }
}