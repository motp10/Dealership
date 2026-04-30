package dealership.domain.details.Body;

import dealership.domain.details.DetailInfo;

public record BodyInfo(String value) implements DetailInfo {

    @Override
    public String Value() {
        return value;
    }

    public BodyInfo {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Body type cannot be empty");
        }
    }
}
