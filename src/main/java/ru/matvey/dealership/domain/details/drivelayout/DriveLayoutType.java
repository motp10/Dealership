package ru.matvey.dealership.domain.details.drivelayout;

import ru.matvey.dealership.domain.details.DetailType;

public record DriveLayoutType(String value) implements DetailType {
    @Override
    public String Value() {
        return value;
    }

    public DriveLayoutType {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Drive type cannot be empty");
        }
    }
}