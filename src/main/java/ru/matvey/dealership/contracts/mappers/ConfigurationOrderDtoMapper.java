package ru.matvey.dealership.contracts.mappers;

import ru.matvey.dealership.contracts.models.CarDto;
import ru.matvey.dealership.contracts.models.ConfigurationOrderDto;
import ru.matvey.dealership.domain.orders.configurationOrder.ConfigurationOrder;

import java.util.UUID;

public class ConfigurationOrderDtoMapper {
    public static ConfigurationOrderDto toDto(ConfigurationOrder order) {
        return new ConfigurationOrderDto(
                order.getClientId(),
                order.getManagerId(),
                CarDtoMapper.toDto(order.getCar()),
                order.getStatus()
        );
    }
}
