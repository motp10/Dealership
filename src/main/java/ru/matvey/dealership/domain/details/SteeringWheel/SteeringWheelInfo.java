package ru.matvey.dealership.domain.details.SteeringWheel;

import ru.matvey.dealership.domain.details.DetailInfo;

public record SteeringWheelInfo(String value) implements DetailInfo {
    @Override
    public String Value() {
        return value;
    }

    public SteeringWheelInfo {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Transmission type cannot be empty");
        }
    }
}