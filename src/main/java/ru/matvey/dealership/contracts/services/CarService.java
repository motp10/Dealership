package ru.matvey.dealership.contracts.services;

import ru.matvey.dealership.contracts.models.CarDto;
import ru.matvey.dealership.contracts.operations.CarComplictation;

import java.util.List;
import java.util.UUID;

public interface CarService {

    UUID addCar(CarComplictation complictation);

    void updateCar(UUID id, CarComplictation complictation);

    void deleteCar(UUID id);
    CarDto getCar(UUID id, UUID clientId);

    List<CarDto> getCars(CarComplictation filter);

    List<CarDto> getAllCars();

}
