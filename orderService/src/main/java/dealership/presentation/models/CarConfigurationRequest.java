package dealership.presentation.models;

import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record CarConfigurationRequest(
        @NotEmpty String brand,
        @NotEmpty String model,
        String bodyType,
        String paint,
        String fuel,
        String transmission,
        String driveLayout,
        String wheel,
        UUID carID
) {}