package ru.matvey.dealership.contracts.services;

import ru.matvey.dealership.contracts.models.CarDto;
import ru.matvey.dealership.contracts.models.ConfigurationOrderDto;
import ru.matvey.dealership.contracts.models.DetailDto;
import ru.matvey.dealership.contracts.models.OrderDto;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.operations.DetailOperation;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    UUID addCar(CarComplictation complictation);
    void updateCar(UUID id, CarComplictation complictation);
    void deleteCar(UUID id);
    CarDto getCar(UUID id);
    List<CarDto> getCars(CarComplictation request);
    List<CarDto> getAllCars();

    UUID addDetail(DetailOperation.Request request);
    void updateDetail(UUID id, DetailOperation.Request request);
    void deleteDetail(UUID id);
    DetailDto getDetail(UUID id);
    List<DetailDto> getAllDetails();

    void updateOrderStatus(UUID id);
    void cancelOrder(UUID id);
    ConfigurationOrderDto getConfigurationOrder(UUID id);
    OrderDto getStockCarOrder(UUID id);
    OrderDto getTestDriveOrder(UUID id);

    List<ConfigurationOrderDto> getAllConfigurationOrders();
    List<OrderDto> getAllStockCarOrders();
    List<OrderDto> getAllTestDriveOrders();

    UUID addUser(String name, String password, String fullname, String role);
    void deleteUser(UUID id);

}