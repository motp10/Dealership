package dealership.presentation.controllers;

import com.autosalon.grpc.CarResponse;
import com.autosalon.grpc.DetailMessage;
import dealership.contracts.models.CarDto;
import dealership.contracts.models.DetailDto;
import dealership.infrastracture.carClient.CarGrpcClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
@Slf4j
public class CarController {

    private final CarGrpcClient carGrpcClient;

    @GetMapping
//    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<List<CarDto>> getAvailableCars() {
        log.info("REST request to fetch all available cars");

        List<CarResponse> grpcCars = carGrpcClient.getAvailableCars();

        List<CarDto> response = grpcCars.stream()
                .map(this::mapToCarDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<CarDto> getCarById(@PathVariable String id) {
        log.info("REST request to fetch car with id: {}", id);

        CarResponse grpcCar = carGrpcClient.getCarById(id);

        return ResponseEntity.ok(mapToCarDto(grpcCar));
    }

    private CarDto mapToCarDto(CarResponse grpcCar) {
        return new CarDto(
                grpcCar.getId(),
                grpcCar.getBrand(),
                grpcCar.getModel(),
                grpcCar.getBaseCost(),
                grpcCar.getTotalPrice(),
                grpcCar.getIsAvailable(),
                mapToDetailDto(grpcCar.getBody()),
                mapToDetailDto(grpcCar.getPaint()),
                mapToDetailDto(grpcCar.getFuel()),
                mapToDetailDto(grpcCar.getTransmission()),
                mapToDetailDto(grpcCar.getDrivelayout())
        );
    }

    private DetailDto mapToDetailDto(DetailMessage grpcDetail) {
        if (grpcDetail.getId().isEmpty()) {
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