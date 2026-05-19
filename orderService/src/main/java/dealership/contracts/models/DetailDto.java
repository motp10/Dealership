package dealership.contracts.models;

public record DetailDto(
        String id,
        String type,
        String info,
        double cost
) {}