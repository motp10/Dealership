package ru.matvey.dealership.contracts.models;

import ru.matvey.dealership.domain.car.Car;

import java.util.*;

public record CarDto(
        UUID id,
        String brand,
        String model,
        double price,
        String body,
        String paint,
        String fuel,
        String transmission,
        String drivelayout,
        String wheel
) {
    public CarDto(Car car) {
        this(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getTotalPrice(),
                car.getBody().Info().Value(),
                car.getCarPaint().Info().Value(),
                car.getFuel().Info().Value(),
                car.getTransmission().Info().Value(),
                car.getDrivelayout().Info().Value(),
                car.getWheel() == null ? null : car.getWheel().Info().Value()
        );
    }
}