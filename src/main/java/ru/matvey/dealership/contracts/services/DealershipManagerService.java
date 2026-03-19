package ru.matvey.dealership.contracts.services;

import ru.matvey.dealership.contracts.models.CarDto;
import ru.matvey.dealership.contracts.models.ConfigurationOrderDto;
import ru.matvey.dealership.contracts.models.OrderDto;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.operations.MakeOrder;
import ru.matvey.dealership.domain.orders.testDriveOrder.TestDriveOrder;

import java.util.List;
import java.util.UUID;

public interface DealershipManagerService {
    CarDto getCar(UUID id);
    List<CarDto> getCars(CarComplictation request);
    List<CarDto> getAllCars();

    OrderDto getStockCarOrder(UUID id);
    List<OrderDto> getAllStockCarOrders();

    ConfigurationOrderDto getConfigurationOrder(UUID id);
    ConfigurationOrderDto getAllConfigurationOrders();

    TestDriveOrder getTestDriveOrder(UUID id);
    List<OrderDto> getAllTestDriveOrders();
    void deleteTestDriveCar(UUID orderId);
    void addTestDriveCar(MakeOrder.Request request);
}
