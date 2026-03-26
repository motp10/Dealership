package ru.matvey.dealership.contracts.services;

import ru.matvey.dealership.contracts.models.ConfigurationOrderDto;
import ru.matvey.dealership.contracts.models.OrderDto;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.operations.MakeOrder;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    UUID makeStockCarOrder(MakeOrder.Request request);

    UUID makeTestDriveOrder(MakeOrder.Request request);
    UUID makeOrderWithConfiguration(CarComplictation complictation, UUID id);

    ConfigurationOrderDto getConfigurationOrder(UUID id);

    List<ConfigurationOrderDto> getAllConfigurationOrders();

    OrderDto getStockCarOrder(UUID id);

    List<OrderDto> getAllStockCarOrders();

    OrderDto getTestDriveOrder(UUID id);

    List<OrderDto> getAllTestDriveOrders();

    void updateOrderStatus(UUID id);

    void cancelOrder(UUID id);
}
