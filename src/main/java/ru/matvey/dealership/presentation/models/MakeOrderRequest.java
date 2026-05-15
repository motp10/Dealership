package ru.matvey.dealership.presentation.models;

import java.util.UUID;

public record MakeOrderRequest (
        UUID clientId,
        UUID carId
){}