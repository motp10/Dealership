package dealership.infrastracture.grpcEntities;

import com.autosalon.grpc.CarResponse;
import com.autosalon.grpc.DetailMessage;
import dealership.domain.car.Car;
import dealership.domain.details.Detail;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarGrpcMapper {

    public CarResponse toCarResponse(Car car) {
        if (car == null) {
            return CarResponse.getDefaultInstance();
        }

        return CarResponse.newBuilder()
                .setId(car.getId().toString())
                .setBrand(car.getBrand())
                .setModel(car.getModel())
                .setBaseCost(car.getBaseCost())
                .setTotalPrice(car.getTotalPrice())
                .setIsAvailable(!car.isRemoved())

                .setBody(toDetailMessage(car.getBody()))
                .setPaint(toDetailMessage(car.getCarPaint()))
                .setFuel(toDetailMessage(car.getFuel()))
                .setTransmission(toDetailMessage(car.getTransmission()))
                .setDrivelayout(toDetailMessage(car.getDrivelayout()))

                .build();
    }

    public List<CarResponse> toCarResponseList(List<Car> cars) {
        if (cars == null) {
            return List.of();
        }

        return cars.stream()
                .map(this::toCarResponse)
                .collect(Collectors.toList());
    }

    private DetailMessage toDetailMessage(Detail detail) {
        if (detail == null) {
            return DetailMessage.getDefaultInstance();
        }

        return DetailMessage.newBuilder()
                .setId(detail.getId().toString())
                .setType(detail.Type())
                .setInfo(detail.getInfo())
                .setCost(detail.Cost())
                .build();
    }
}