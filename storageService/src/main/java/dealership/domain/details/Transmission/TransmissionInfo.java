package dealership.domain.details.Transmission;

import dealership.domain.details.DetailInfo;

public record TransmissionInfo(String value) implements DetailInfo {

    @Override
    public String Value() {
        return value;
    }

    public TransmissionInfo {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Transmission type cannot be empty");
        }
    }
}