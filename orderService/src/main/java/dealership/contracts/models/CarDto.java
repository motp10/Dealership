package dealership.contracts.models;

public record CarDto(
        String id,
        String brand,
        String model,
        double baseCost,
        double totalPrice,
        boolean isAvailable,
        DetailDto body,
        DetailDto paint,
        DetailDto fuel,
        DetailDto transmission,
        DetailDto drivelayout
) {}