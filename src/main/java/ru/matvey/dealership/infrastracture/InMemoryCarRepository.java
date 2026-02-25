package ru.matvey.dealership.infrastracture;

import ru.matvey.dealership.Abstraction.CarsRepository;
import ru.matvey.dealership.domain.car.Car;
import ru.matvey.dealership.domain.details.Body.BodyType;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Transmission.TransmissionType;
import ru.matvey.dealership.domain.details.Wheel.WheelType;
import ru.matvey.dealership.domain.details.color.PaintType;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayoutType;
import ru.matvey.dealership.domain.details.fuel.FuelType;

import java.util.*;

public class InMemoryCarRepository implements CarsRepository {
    private final List<Car> storage = new ArrayList<>();

    @Override
    public void addCar(Car car) {
        storage.add(car);
    }

    @Override
    public void deleteById(UUID id) {
        storage.removeIf(x -> x.getId().equals(id));
    }

    @Override
    public Car findById(UUID id) {
        return storage.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Car> getdAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public List<Car> findByCriteria(Cost maxPrice,
                                    String brand,
                                    String model,
                                    BodyType bodyType,
                                    FuelType fuelType,
                                    TransmissionType transmissionType,
                                    DriveLayoutType drivelayoutType,
                                    PaintType color,
                                    WheelType wheel) {
        return storage.stream()
                .filter(car -> brand == null || car.getBrand().equalsIgnoreCase(brand))
                .filter(car -> model == null || car.getModel().equalsIgnoreCase(model))
                .filter(car -> maxPrice == null || car.getTotalPrice() <= maxPrice.value())
                .filter(car -> bodyType == null || bodyType.equals(car.getBody().Type()))
                .filter(car -> transmissionType == null || transmissionType.equals(car.getTransmission().Type()))
                .filter(car -> drivelayoutType == null || drivelayoutType.equals(car.getDrivelayout().Type()))
                .filter(car -> color == null || color.equals(car.getCarPaint().Type()))
                .toList();
    }
}