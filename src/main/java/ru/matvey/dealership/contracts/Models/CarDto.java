package ru.matvey.dealership.contracts.Models;

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
        List<String> details
) {
    public CarDto(Car car) {
        this(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getTotalPrice(),
                car.getBody().Type().Value(),
                car.getCarPaint().Type().Value(),
                car.getFuel().Type().Value(),
                car.getTransmission().Type().Value(),
                car.getDrivelayout().Type().Value(),
                car.getSelectedComponents().values().stream()
                        .map(detail -> detail.Type().Value())
                        .toList()
        );
    }
}