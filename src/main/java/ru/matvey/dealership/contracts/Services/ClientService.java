package ru.matvey.dealership.contracts.Services;

import ru.matvey.dealership.contracts.Models.CarDto;
import ru.matvey.dealership.contracts.Operations.CarComplictation;
import ru.matvey.dealership.contracts.Operations.MakeOrder;
import ru.matvey.dealership.domain.car.Car;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    List<Car> getAllCars();

    void makeStockCarOrder(MakeOrder.Request request);

    void makeOrderWithComplictation(CarComplictation request, UUID clientId);

    void makeTestDriveOrder(MakeOrder.Request request);

    List<Car> getCars(CarComplictation request);
}
