package ru.matvey.dealership.presentation.controllers;

import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;
import ru.matvey.dealership.contracts.models.ConfigurationOrderDto;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.services.OrderService;
import ru.matvey.dealership.contracts.services.UserService;
import ru.matvey.dealership.presentation.models.CarConfigurationRequest;

import java.util.List;
import java.util.UUID;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@RestController
@RequestMapping("/api/configurationCars")
@RequiredArgsConstructor
public class ConfigurationCarOrderController {
    private final OrderService orderService;
    private final MeterRegistry meterRegistry;

    @PostConstruct
    void registerBusinessMetrics() {
        meterRegistry.counter("dealership_configuration_orders_created_total");
    }
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
        UUID orderId = orderService.makeOrderWithConfiguration(complictation, request.clientId());
        meterRegistry.counter("dealership_configuration_orders_created_total").increment();
        log.info("Configuration order request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "ConfigurationCarOrderController"),
            kv("operation", "create"),
            kv("orderId", orderId),
            kv("clientId", request.clientId()),
            kv("model", request.model()));
        return orderId;
    }
    @PatchMapping("/update")
    public void updateOrderStatus(@RequestBody UUID id) {
        orderService.updateOrderStatus(id);
        log.info("Configuration order status update request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "ConfigurationCarOrderController"),
            kv("operation", "advanceStatus"),
            kv("orderId", id));
    }
    @PatchMapping("/cancel")
    public void cancelOrder(@RequestBody UUID id) {
        orderService.cancelOrder(id);
        log.info("Configuration order cancellation request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "ConfigurationCarOrderController"),
            kv("operation", "cancel"),
            kv("orderId", id));
    }
    @GetMapping("/{id}")
    public ConfigurationOrderDto getOrder(@RequestBody UUID id) {
        ConfigurationOrderDto order = orderService.getConfigurationOrder(id);
        log.debug("Configuration order retrieval request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "ConfigurationCarOrderController"),
            kv("operation", "getById"),
            kv("orderId", id));
        return order;
    }
    @GetMapping
    public List<ConfigurationOrderDto> getAllOrders() {
        List<ConfigurationOrderDto> orders = orderService.getAllConfigurationOrders();
        log.debug("Configuration order list request completed",
            kv("component", "presentation-controller"),
            kv("controller_name", "ConfigurationCarOrderController"),
            kv("operation", "getAll"),
            kv("count", orders.size()));
        return orders;
    }
}