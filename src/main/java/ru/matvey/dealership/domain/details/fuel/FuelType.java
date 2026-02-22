package ru.matvey.dealership.domain.details.fuel;

import ru.matvey.dealership.domain.details.IDetailType;

public record FuelType(String value) implements IDetailType {
    @Override
    public String Value() {
        return value;
    };

    public FuelType {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Wheel type cannot be empty");
        }
    }
}
