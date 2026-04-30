package ru.matvey.dealership.domain.details.Wheel;

import ru.matvey.dealership.domain.details.DetailInfo;

public record WheelInfo(String value) implements DetailInfo {

    @Override
    public String Value() {
        return value;
    };

    public WheelInfo {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Wheel type cannot be empty");
        }
    }
}
