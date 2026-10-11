package ru.matvey.dealership.presentation.controllers;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.matvey.dealership.contracts.models.OrderDto;
import ru.matvey.dealership.contracts.operations.MakeOrder;
import ru.matvey.dealership.contracts.services.OrderService;
import ru.matvey.dealership.presentation.models.MakeOrderRequest;

import java.util.List;
import java.util.UUID;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@RestController
@RequestMapping("/api/stockCars")
@RequiredArgsConstructor
public class StockCarOrderController {
    private final OrderService orderService;

    @PostMapping
    public UUID makeOrder(@RequestBody MakeOrderRequest request) {
        MakeOrder.Request orderRequest = new MakeOrder.Request(request.clientId(), request.carId());
        UUID orderId = orderService.makeStockCarOrder(orderRequest);
        log.info("Stock car order request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "StockCarOrderController"),
            kv("operation", "create"),
            kv("orderId", orderId),
            kv("carId", request.carId()));
        return orderId;
    }
    @PatchMapping("/update")
    public void updateOrderStatus(@RequestBody UUID id) {
        orderService.updateOrderStatus(id);
        log.info("Stock car order status update request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "StockCarOrderController"),
            kv("operation", "advanceStatus"),
            kv("orderId", id));
    }
    @PatchMapping("/cancel")
    public void cancelOrder(@RequestBody UUID id) {
        orderService.cancelOrder(id);
        log.info("Stock car order cancellation request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "StockCarOrderController"),
            kv("operation", "cancel"),
            kv("orderId", id));
    }
    @GetMapping("/{id}")
    public OrderDto getOrder(@RequestBody UUID id) {
        OrderDto order = orderService.getStockCarOrder(id);
        log.debug("Stock car order retrieval request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "StockCarOrderController"),
            kv("operation", "getById"),
            kv("orderId", id));
        return order;
    }
    @GetMapping
    public List<OrderDto> getAllOrders() {
        List<OrderDto> orders = orderService.getAllStockCarOrders();
        log.debug("Stock car order list request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "StockCarOrderController"),
            kv("operation", "getAll"),
            kv("count", orders.size()));
        return orders;
    }
}
