package dealership.contracts.mappers;


import dealership.contracts.models.CarDto;
import dealership.domain.car.Car;

public class CarDtoMapper {
    public static CarDto toDto(Car car) {
        if (car == null) {
            return null;
        }

        CarDto dto = new CarDto(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getTotalPrice(),
                car.getBody().Info().Value(),
                car.getCarPaint().Info().Value(),
                car.getFuel().Info().Value(),
                car.getTransmission().Info().Value(),
                car.getDrivelayout().Info().Value()
        );
        return dto;
    }
}
