package ru.matvey.dealership.contracts.services;

import ru.matvey.dealership.contracts.models.CarDto;
import ru.matvey.dealership.contracts.models.DetailDto;
import ru.matvey.dealership.contracts.operations.CarComplictation;
import ru.matvey.dealership.contracts.operations.DetailOperation;

import java.util.List;
import java.util.UUID;

public interface WarehouseAdminService {
    void addCar(CarComplictation complictation);
    void deleteCar(UUID id);
    void updateCar(UUID id, CarComplictation complictation);
    CarDto getCar(UUID id);
    List<CarDto> getAllCars();

    void addDetail(DetailOperation.Request request);
    void deleteDetail(UUID detailId);
    void updateDetail(DetailOperation.Request request);
    DetailDto getDetail(UUID detailId);
    List<DetailDto> getAllDetails();

}