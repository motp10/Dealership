package ru.matvey.dealership.domain.details.Transmission;

import ru.matvey.dealership.domain.details.IDetailType;

public record TransmissionType(String value) implements IDetailType {

    @Override
    public String Value() {
        return value;
    }

    public TransmissionType {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Transmission type cannot be empty");
        }
    }
}