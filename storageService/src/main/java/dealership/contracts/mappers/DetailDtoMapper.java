package dealership.contracts.mappers;

import dealership.contracts.models.DetailDto;
import dealership.domain.details.Detail;

public class DetailDtoMapper {
    public static DetailDto toDto(Detail detail) {
        return new DetailDto(detail.getId(), detail.Type(), detail.Info().Value(), detail.Cost(), detail.CompatibleModels());
    }
}
