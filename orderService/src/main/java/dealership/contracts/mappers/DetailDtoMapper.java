package dealership.contracts.mappers;

import dealership.contracts.models.DetailDto;
import dealership.domain.details.Detail;

public class DetailDtoMapper {
    public static DetailDto toDto(Detail detail) {
        return new DetailDto(detail.Type(), detail.Info().Value(), detail.Cost());
    }
}
