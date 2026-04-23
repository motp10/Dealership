package ru.matvey.dealership.presentation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.matvey.dealership.contracts.models.OrderDto;
import ru.matvey.dealership.contracts.services.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/testDrive")
@RequiredArgsConstructor
public class TestDriveOrderController {
    private final OrderService orderService;

    @PostMapping
    public UUID makeOrder(@PathVariable UUID carId, JwtAuthenticationToken auth) {
        UUID clientId = UUID.fromString(auth.getToken().getSubject());
        return orderService.makeTestDriveOrder(carId, clientId);
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
    public OrderDto getOrder(@RequestBody UUID id, JwtAuthenticationToken auth) {
        UUID clientId = UUID.fromString(auth.getToken().getSubject());
        return orderService.getTestDriveOrder(id, clientId);
    }
    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllTestDriveOrders();
    }
}