package dealership.domain.details.color;

import dealership.domain.details.DetailInfo;
public record PaintInfo(String value) implements DetailInfo {
    @Override
    public String Value() {
        return value;
    }

    public PaintInfo {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("Color cannot be empty");
    }
}
