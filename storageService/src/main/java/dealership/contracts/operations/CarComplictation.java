package dealership.contracts.operations;

public record CarComplictation(
    double maxPrice,
    String brand,
    String model,
    String bodyType,
    String fuelType,
    String transmissionType,
    String drivelayoutType,
    String color,
    String wheel
) {
    public static CarComplictation empty() {
        return new CarComplictation(0, null, null, null, null, null, null, null, null);
    }
}
