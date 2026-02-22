package ru.matvey.dealership.contracts.Models;

import ru.matvey.dealership.domain.details.IDetail;

public record DetailDto(
        String detailInfo,
        double price
) {
    public DetailDto(IDetail detail) {
        this(detail.Type().Value(), detail.Cost());
    }
}
