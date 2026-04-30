package dealership.contracts.services;

import dealership.contracts.models.ConfigurationOrderDto;
import dealership.contracts.models.OrderDto;
import dealership.contracts.operations.CarComplictation;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    UUID makeStockCarOrder(UUID carID, UUID clientId);

    UUID makeTestDriveOrder(UUID carID, UUID clientId);

    UUID makeOrderWithConfiguration(CarComplictation complictation, UUID clientId);

    ConfigurationOrderDto getConfigurationOrder(UUID id, UUID clientId);

    List<ConfigurationOrderDto> getAllConfigurationOrders();

    OrderDto getStockCarOrder(UUID id, UUID clientId);

    List<OrderDto> getAllStockCarOrders();

    OrderDto getTestDriveOrder(UUID id, UUID clientId);

    List<OrderDto> getAllTestDriveOrders();

    void updateOrderStatus(UUID id);

    void cancelOrder(UUID id, UUID clientId);
}
