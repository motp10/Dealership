package ru.matvey.dealership.presentation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.matvey.dealership.contracts.models.ConfigurationOrderDto;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.services.AdminService;
import ru.matvey.dealership.contracts.services.ClientService;
import ru.matvey.dealership.presentation.models.CarConfigurationRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/configurationCars")
@RequiredArgsConstructor
public class ConfigurationCarOrderController {
    private final AdminService adminService;
    private final ClientService clientService;

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
        return clientService.makeOrderWithComplictation(complictation, request.clientId());
    }
    @PatchMapping("/update")
    public void updateOrderStatus(@RequestBody UUID id) {
        adminService.updateOrderStatus(id);
    }
    @PatchMapping("/cancel")
    public void cancelOrder(@RequestBody UUID id) {
        adminService.cancelOrder(id);
    }
    @GetMapping("/{id}")
    public ConfigurationOrderDto getOrder(@RequestBody UUID id) {
        return adminService.getConfigurationOrder(id);
    }
    @GetMapping
    public List<ConfigurationOrderDto> getAllOrders() {
        return adminService.getAllConfigurationOrders();
    }
}