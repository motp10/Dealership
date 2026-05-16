package ru.matvey.dealership.contracts.mappers;

import ru.matvey.dealership.contracts.models.OrderDto;
import ru.matvey.dealership.domain.orders.Order;

public class OrderDtoMapper {
    public static OrderDto toDto(Order order) {
        return new OrderDto(order.getClientId(), order.getManagerId(), order.getCarId(), order.getStatus());
    }
}
