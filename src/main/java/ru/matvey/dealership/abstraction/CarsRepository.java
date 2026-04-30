package ru.matvey.dealership.abstraction;

import ru.matvey.dealership.domain.details.Body.BodyInfo;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Transmission.TransmissionInfo;
import ru.matvey.dealership.domain.details.Wheel.WheelInfo;
import ru.matvey.dealership.domain.details.color.PaintInfo;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayoutInfo;
import ru.matvey.dealership.domain.details.fuel.FuelInfo;

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
                              BodyInfo bodyType,
                              FuelInfo fuelType,
                              TransmissionInfo transmissionType,
                              DriveLayoutInfo drivelayoutType,
                              PaintInfo color,
                              WheelInfo wheel);
}
