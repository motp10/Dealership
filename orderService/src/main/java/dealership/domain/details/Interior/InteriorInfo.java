package dealership.domain.details.Interior;

import dealership.domain.details.DetailInfo;

public record InteriorInfo(String value) implements DetailInfo {

    @Override
    public String Value() {
        return value;
    }

    public InteriorInfo {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Interior type cannot be empty");
        }
    }
}