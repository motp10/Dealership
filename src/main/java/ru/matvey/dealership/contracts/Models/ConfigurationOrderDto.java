package ru.matvey.dealership.contracts.Models;

import ru.matvey.dealership.domain.car.Car;
import ru.matvey.dealership.domain.orders.configurationOrder.ConfigurationOrder;
import ru.matvey.dealership.domain.users.ClientUser;
import ru.matvey.dealership.domain.users.DealeshipManagerUser;

import java.util.UUID;

public record ConfigurationOrderDto(
    UUID clientId,
    UUID managerId,
    CarDto car,
    String status
) {
    public ConfigurationOrderDto(ConfigurationOrder order) {
        this(order.getClientId(), order.getManagerId(), new CarDto(order.getCar()), order.getOrderStatus().getName());
    }
}
