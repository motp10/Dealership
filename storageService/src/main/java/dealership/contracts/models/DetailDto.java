package dealership.contracts.models;


import dealership.domain.details.Detail;

import java.util.Set;
import java.util.UUID;

public record DetailDto(
        UUID id,
        String detailType,
        String detailInfo,
        double cost,
        Set<String> compatibleModels
) {
    public DetailDto(Detail detail) {
        this(detail.getId(), detail.Type(), detail.Info().Value(), detail.Cost(), detail.CompatibleModels());
    }
}
