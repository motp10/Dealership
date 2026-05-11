package dealership.contracts.models;

import dealership.domain.orders.configurationOrder.ConfigurationOrder;

import java.util.UUID;

public record ConfigurationOrderDto(
    UUID clientId,
    UUID managerId,
    UUID carId,
    String status
) {
    public ConfigurationOrderDto(ConfigurationOrder order) {
        this(order.getClientId(), order.getManagerId(), order.getCarId(), order.getStatus());
    }
}
