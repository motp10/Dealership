package ru.matvey.dealership.domain.details.fuel;

import ru.matvey.dealership.domain.details.DetailInfo;

public record FuelInfo(String value) implements DetailInfo {
    @Override
    public String Value() {
        return value;
    };

    public FuelInfo {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Wheel type cannot be empty");
        }
    }
}
