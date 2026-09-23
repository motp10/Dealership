package ru.matvey.dealership.presentation.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record CarConfigurationRequest(
        @NotBlank String brand,
        @NotBlank String model,
        @NotBlank String bodyType,
        @NotBlank String paint,
        @NotBlank String fuel,
        @NotBlank String transmission,
        @NotBlank String driveLayout,
        String wheel,
        UUID clientId,
        @NotNull @PositiveOrZero Double baseCost
) {}