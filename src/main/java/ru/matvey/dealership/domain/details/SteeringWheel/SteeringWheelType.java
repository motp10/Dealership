package ru.matvey.dealership.domain.details.SteeringWheel;

import ru.matvey.dealership.domain.details.DetailType;

public record SteeringWheelType(String value) implements DetailType {
    @Override
    public String Value() {
        return value;
    }

    public SteeringWheelType {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Transmission type cannot be empty");
        }
    }
}