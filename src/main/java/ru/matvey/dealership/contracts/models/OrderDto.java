package ru.matvey.dealership.contracts.models;

import java.util.UUID;

public record OrderDto(
    UUID clientId,
    UUID managerId,
    UUID carId,
    String status
) {
    public OrderDto(UUID clientId, UUID managerId, UUID carId, String status) {
        this.clientId = clientId;
        this.managerId = managerId;
        this.carId = carId;
        this.status = status;
    }
}
