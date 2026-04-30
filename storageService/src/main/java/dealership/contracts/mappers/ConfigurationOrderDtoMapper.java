package dealership.contracts.mappers;

import dealership.contracts.models.ConfigurationOrderDto;
import dealership.domain.orders.configurationOrder.ConfigurationOrder;

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
