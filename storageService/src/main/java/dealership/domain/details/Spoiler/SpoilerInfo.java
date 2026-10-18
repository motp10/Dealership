package dealership.domain.details.Spoiler;

import dealership.domain.details.DetailInfo;

public record SpoilerInfo(String value) implements DetailInfo {
    @Override
    public String Value() {
        return value;
    }
}