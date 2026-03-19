package ru.matvey.dealership.contracts.services;

import ru.matvey.dealership.contracts.models.CarDto;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.operations.MakeOrder;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    UUID makeStockCarOrder(MakeOrder.Request request);

    UUID makeOrderWithComplictation(CarComplictation request, UUID clientId);

    UUID makeTestDriveOrder(MakeOrder.Request request);

    CarDto getCar(UUID id);
    List<CarDto> getCars(CarComplictation request);
    List<CarDto> getAllCars();

}