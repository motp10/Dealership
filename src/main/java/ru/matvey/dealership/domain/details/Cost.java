package ru.matvey.dealership.domain.details;

import ru.matvey.dealership.domain.exceptions.DomainValidationException;

public record Cost(double value) {
    public Cost {
        if (value < 0) {
            throw new DomainValidationException("Cost cannot be negative");
        }
    }
}
