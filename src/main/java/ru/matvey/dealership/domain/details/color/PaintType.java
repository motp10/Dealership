package ru.matvey.dealership.domain.details.color;

import ru.matvey.dealership.domain.details.DetailType;
public record PaintType(String value) implements DetailType {
    @Override
    public String Value() {
        return value;
    }

    public PaintType {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("Color cannot be empty");
    }
}
