package ru.matvey.dealership.domain.details.drivelayout;

import ru.matvey.dealership.domain.details.DetailInfo;

public record DriveLayoutInfo(String value) implements DetailInfo {
    @Override
    public String Value() {
        return value;
    }

    public DriveLayoutInfo {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Drive type cannot be empty");
        }
    }
}