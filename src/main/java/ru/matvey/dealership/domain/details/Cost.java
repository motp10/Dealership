package ru.matvey.dealership.domain.details;

import ru.matvey.dealership.domain.exceptions.DomainValidationException;

public record Cost(double value) {
    public Cost {
        if (!Double.isFinite(value) || value < 0) {
            throw new DomainValidationException("Cost must be finite and non-negative");
        }
    }
}
