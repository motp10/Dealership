package ru.matvey.dealership.presentation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.matvey.dealership.contracts.models.OrderDto;
import ru.matvey.dealership.contracts.operations.MakeOrder;
import ru.matvey.dealership.contracts.services.AdminService;
import ru.matvey.dealership.contracts.services.ClientService;
import ru.matvey.dealership.presentation.models.MakeOrderRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/testDrive")
@RequiredArgsConstructor
public class TestDriveOrderController {
    private final AdminService adminService;
    private final ClientService clientService;

    @PostMapping
    public UUID makeOrder(@RequestBody MakeOrderRequest request) {
        MakeOrder.Request orderRequest = new MakeOrder.Request(request.clientId(), request.carId());
        return clientService.makeTestDriveOrder(orderRequest);
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
    public OrderDto getOrder(@RequestBody UUID id) {
        return adminService.getTestDriveOrder(id);
    }
    @GetMapping
    public List<OrderDto> getAllOrders() {
        return adminService.getAllTestDriveOrders();
    }
}