package ru.matvey.dealership.domain.details.Body;

import ru.matvey.dealership.domain.details.DetailType;

public record BodyType(String value) implements DetailType {

    @Override
    public String Value() {
        return value;
    }

    public BodyType {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Body type cannot be empty");
        }
    }
}
