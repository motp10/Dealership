package ru.matvey.dealership.contracts.Models;

import java.util.UUID;

public record OrderDto(
    UUID clientId,
    UUID mangerId,
    UUID carId,
    String status
) {}
