package dealership.contracts.services;


import dealership.contracts.models.CarDto;
import dealership.contracts.operations.CarComplictation;

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
