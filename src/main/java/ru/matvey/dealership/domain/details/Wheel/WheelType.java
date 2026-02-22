package ru.matvey.dealership.domain.details.Wheel;

import ru.matvey.dealership.domain.details.IDetailType;

public record WheelType(String value) implements IDetailType {

    @Override
    public String Value() {
        return value;
    };

    public WheelType {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Wheel type cannot be empty");
        }
    }
}
