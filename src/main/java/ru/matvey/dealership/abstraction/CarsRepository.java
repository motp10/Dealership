package ru.matvey.dealership.abstraction;

import ru.matvey.dealership.domain.details.Body.BodyType;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Transmission.TransmissionType;
import ru.matvey.dealership.domain.details.Wheel.WheelType;
import ru.matvey.dealership.domain.details.color.PaintType;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayoutType;
import ru.matvey.dealership.domain.details.fuel.FuelType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ru.matvey.dealership.domain.car.Car;

public interface CarsRepository {
    void add(Car car);
    void delete(UUID id);
    Optional<Car> findById(UUID id);
    List<Car> getAll();
    List<Car> findByCriteria( Cost maxPrice,
                              String brand,
                              String model,
                              BodyType bodyType,
                              FuelType fuelType,
                              TransmissionType transmissionType,
                              DriveLayoutType drivelayoutType,
                              PaintType color,
                              WheelType wheel);
}
