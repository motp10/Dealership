package ru.matvey.dealership.contracts.Services;

import ru.matvey.dealership.contracts.Models.CarDto;
import ru.matvey.dealership.contracts.Models.DetailDto;
import ru.matvey.dealership.contracts.Operations.CarComplictation;
import ru.matvey.dealership.contracts.Operations.DetailOperation;

import java.util.List;
import java.util.UUID;

public interface WarehouseAdminService {
    List<CarDto> getAllCars();
    List<DetailDto> getAllDetails();

    CarDto getCar(UUID carId);

    DetailDto getDetail(UUID detailId);

    void addCar(CarComplictation complictation);

    void addDetail(DetailOperation.Request request);

    void deleteCar(UUID carId);

    void removeDetail(UUID detailId);
}
