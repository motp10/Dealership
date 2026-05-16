package ru.matvey.dealership.contracts.mappers;

import ru.matvey.dealership.contracts.models.DetailDto;
import ru.matvey.dealership.domain.details.Detail;

public class DetailDtoMapper {
    public static DetailDto toDto(Detail detail) {
        return new DetailDto(detail.Type(), detail.Info().Value(), detail.Cost());
    }
}
