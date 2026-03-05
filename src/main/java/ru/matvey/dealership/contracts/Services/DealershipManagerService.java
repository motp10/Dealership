package ru.matvey.dealership.contracts.Services;

import ru.matvey.dealership.contracts.Models.CarDto;
import ru.matvey.dealership.contracts.Models.ConfigurationOrderDto;
import ru.matvey.dealership.contracts.Models.OrderDto;
import ru.matvey.dealership.contracts.Operations.CarComplictation;
import ru.matvey.dealership.contracts.Operations.MakeOrder;
import ru.matvey.dealership.domain.car.Car;
import ru.matvey.dealership.domain.orders.stockCarOrder.OrderStockCar;
import ru.matvey.dealership.domain.orders.testDriveOrder.TestDriveOrder;

import java.util.List;
import java.util.UUID;

public interface DealershipManagerService {

    List<Car> getAllCars();

    List<Car> getCars(CarComplictation request, UUID clientId);

    List<OrderStockCar> getStockCarOrders();

    ConfigurationOrderDto getConfigurationCarOrder(UUID orderId);

    void deleteTestDriveCar(UUID orderId);

    void addTestDriveCar(MakeOrder.Request request);

    List<TestDriveOrder> getTestDriveOrders();
}
