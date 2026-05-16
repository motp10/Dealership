package ru.matvey.dealership.contracts.models;

import ru.matvey.dealership.domain.orders.configurationOrder.ConfigurationOrder;

import java.util.UUID;

public record ConfigurationOrderDto(
    UUID clientId,
    UUID managerId,
    CarDto car,
    String status
) {
    public ConfigurationOrderDto(ConfigurationOrder order) {
        this(order.getClientId(), order.getManagerId(), new CarDto(order.getCar()), order.getStatus());
    }
}
