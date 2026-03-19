package ru.matvey.dealership.contracts.models;

import ru.matvey.dealership.domain.details.Detail;

public record DetailDto(
        String detailType,
        String detailInfo,
        double cost
) {
    public DetailDto(Detail detail) {
        this(detail.Type(), detail.Info().Value(), detail.Cost());
    }
}
