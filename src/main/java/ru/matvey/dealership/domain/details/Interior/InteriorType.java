package ru.matvey.dealership.domain.details.Interior;

import ru.matvey.dealership.domain.details.IDetailType;

public record InteriorType(String value) implements IDetailType {

    @Override
    public String Value() {
        return value;
    }

    public InteriorType {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Interior type cannot be empty");
        }
    }
}