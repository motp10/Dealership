package dealership.infrastracture.grpcEntities;


import com.autosalon.grpc.CarListResponse;
import com.autosalon.grpc.CarResponse;
import com.autosalon.grpc.CarStorageServiceGrpc;
import com.autosalon.grpc.Empty;
import dealership.abstraction.CarsRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GrpcServer extends CarStorageServiceGrpc.CarStorageServiceImplBase {

    private final CarsRepository carRepository;
    private final CarGrpcMapper carGrpcMapper;

    @Override
    @Transactional(readOnly = true)
    public void getAvailableCars(Empty request, StreamObserver<CarListResponse> responseObserver) {

        List<dealership.domain.car.Car> carsEntity = carRepository.getAll();

        List<CarResponse> grpcCars = carGrpcMapper.toCarResponseList(carsEntity);

        CarListResponse response = CarListResponse.newBuilder()
                .addAllCars(grpcCars)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}