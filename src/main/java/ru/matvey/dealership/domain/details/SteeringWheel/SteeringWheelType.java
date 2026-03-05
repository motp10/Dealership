package ru.matvey.dealership.domain.details.SteeringWheel;

import ru.matvey.dealership.domain.details.IDetailType;

public record SteeringWheelType(String value) implements IDetailType {
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