package ru.matvey.dealership.contracts.Services;

import ru.matvey.dealership.contracts.Models.CarDto;
import ru.matvey.dealership.contracts.Models.DetailDto;
import ru.matvey.dealership.contracts.Operations.CarComplictation;
import ru.matvey.dealership.contracts.Operations.DetailOperation;
import ru.matvey.dealership.domain.car.Car;
import ru.matvey.dealership.domain.details.IDetail;
import ru.matvey.dealership.domain.orders.configurationOrder.ConfigurationOrder;
import ru.matvey.dealership.domain.orders.stockCarOrder.OrderStockCar;
import ru.matvey.dealership.domain.orders.testDriveOrder.TestDriveOrder;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    void addCar(CarComplictation complictation);
    void updateCar(UUID id, CarComplictation complictation);
    void deleteCar(UUID id);
    List<Car> getAllCars();
    void addDetail(IDetail detail);
    void updateDetail(UUID id, DetailOperation.Request updatedDetail);
    void removeDetail(UUID id);
    List<IDetail> getAllDetails();
    List<ConfigurationOrder> getAllConfigurationOrders();
    void updateOrderConfigurationStatus(UUID orderId);
    void cancelConfigurationOrder(UUID orderId);
    List<OrderStockCar> getAllStockOrders();
    void updateStockOrderStatus(UUID orderId, String status);
    void cancelStockOrder(UUID orderId);
    List<TestDriveOrder> getAllTestDriveOrders();
    void updateTestDriveStatus(UUID orderId, String status);
    void cancelTestDriveOrder(UUID orderId);
}