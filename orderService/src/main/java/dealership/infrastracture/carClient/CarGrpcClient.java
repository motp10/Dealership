package dealership.infrastracture.carClient;

import com.autosalon.grpc.CarIdRequest;
import com.autosalon.grpc.CarListResponse;
import com.autosalon.grpc.CarResponse;
import com.autosalon.grpc.CarStorageServiceGrpc;
import com.autosalon.grpc.Empty;
import dealership.exceptions.StorageServiceUnavailableException;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarGrpcClient {

    private final CarStorageServiceGrpc.CarStorageServiceBlockingStub carStorageStub;

    public List<CarResponse> getAvailableCars() {
        log.info("Sending gRPC request to StorageService to get available cars...");

        try {
            CarListResponse response = carStorageStub
                    .withDeadlineAfter(3, TimeUnit.SECONDS)
                    .getAvailableCars(Empty.newBuilder().build());

            log.info("Successfully fetched {} cars from StorageService", response.getCarsCount());
            return response.getCarsList();

        } catch (StatusRuntimeException e) {
            log.error("gRPC Error: StorageService is unavailable or timeout exceeded. Status: {}", e.getStatus().getCode());
            throw new StorageServiceUnavailableException("StorageService is unavailable right now");
        }
    }

    public CarResponse getCarById(String carId) {
        log.info("Sending gRPC request to StorageService for car id: {}", carId);

        try {
            CarIdRequest request = CarIdRequest.newBuilder().setId(carId).build();

            return carStorageStub
                    .withDeadlineAfter(2, TimeUnit.SECONDS)
                    .getCarById(request);

        } catch (StatusRuntimeException e) {
            log.error("gRPC Error while fetching car id {}. Status: {}", carId, e.getStatus().getCode());
            throw new StorageServiceUnavailableException("StorageService is unavailable right now");
        }
    }
}