package ru.matvey.dealership.contracts.Operations;

import lombok.NonNull;

import java.util.UUID;

public record CarComplictation(
    double maxPrice,
    String brand,
    String model,
    String bodyType,
    String fuelType,
    String transmissionType,
    String drivelayoutType,
    String color,
    String wheel
) {}
