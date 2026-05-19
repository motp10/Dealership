package dealership.contracts.mappers;

import com.autosalon.grpc.CarResponse;
import com.autosalon.grpc.DetailMessage;
import dealership.contracts.models.CarDto;
import dealership.contracts.models.DetailDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarDtoMapper {

    public CarDto toDto(CarResponse grpcCar) {
        if (grpcCar == null) {
            return null;
        }

        return new CarDto(
                grpcCar.getId(),
                grpcCar.getBrand(),
                grpcCar.getModel(),
                grpcCar.getBaseCost(),
                grpcCar.getTotalPrice(),
                grpcCar.getIsAvailable(),
                mapDetail(grpcCar.getBody()),
                mapDetail(grpcCar.getPaint()),
                mapDetail(grpcCar.getFuel()),
                mapDetail(grpcCar.getTransmission()),
                mapDetail(grpcCar.getDrivelayout())
        );
    }

    public List<CarDto> toDtoList(List<CarResponse> grpcCars) {
        if (grpcCars == null) {
            return List.of();
        }

        return grpcCars.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private DetailDto mapDetail(DetailMessage grpcDetail) {
        if (grpcDetail == null || grpcDetail.getId().isEmpty()) {
            return null;
        }

        return new DetailDto(
                grpcDetail.getId(),
                grpcDetail.getType(),
                grpcDetail.getInfo(),
                grpcDetail.getCost()
        );
    }
}