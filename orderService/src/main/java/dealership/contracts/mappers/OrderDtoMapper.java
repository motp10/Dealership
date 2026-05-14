package dealership.contracts.mappers;

import dealership.contracts.models.OrderDto;
import dealership.domain.orders.Order;

public class OrderDtoMapper {
    public static OrderDto toDto(Order order) {
        return new OrderDto(order.getClientId(), order.getManagerId(), order.getCarId(), order.getStatus());
    }
}
