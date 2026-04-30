package dealership.domain.details;

import dealership.exceptions.DomainValidationException;

public record Cost(double value) {
    public Cost {
        if (value < 0) {
            throw new DomainValidationException("Cost cannot be negative");
        }
    }
}
