package ru.matvey.dealership.contracts.services;

import ru.matvey.dealership.contracts.models.ConfigurationOrderDto;
import ru.matvey.dealership.contracts.models.OrderDto;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.operations.MakeOrder;

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
