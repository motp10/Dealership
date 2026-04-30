package dealership.abstraction;


import dealership.domain.car.Car;
import dealership.domain.details.Body.BodyInfo;
import dealership.domain.details.Cost;
import dealership.domain.details.Transmission.TransmissionInfo;
import dealership.domain.details.Wheel.WheelInfo;
import dealership.domain.details.color.PaintInfo;
import dealership.domain.details.drivelayout.DriveLayoutInfo;
import dealership.domain.details.fuel.FuelInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
