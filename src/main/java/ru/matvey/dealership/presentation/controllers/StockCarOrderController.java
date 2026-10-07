package ru.matvey.dealership.presentation.controllers;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.matvey.dealership.contracts.models.OrderDto;
import ru.matvey.dealership.contracts.operations.MakeOrder;
import ru.matvey.dealership.contracts.services.OrderService;
import ru.matvey.dealership.contracts.services.UserService;
import ru.matvey.dealership.presentation.models.MakeOrderRequest;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/stockCars")
@RequiredArgsConstructor
public class StockCarOrderController {
    OrderService orderService;

    @PostMapping
    public UUID makeOrder(@RequestBody MakeOrderRequest request) {
        MakeOrder.Request orderRequest = new MakeOrder.Request(request.clientId(), request.carId());
        return orderService.makeStockCarOrder(orderRequest);
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
    public OrderDto getOrder(@RequestBody UUID id) {
        return orderService.getStockCarOrder(id);
    }
    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllStockCarOrders();
    }
}
