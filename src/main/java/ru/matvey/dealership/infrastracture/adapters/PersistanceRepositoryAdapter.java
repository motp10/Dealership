package ru.matvey.dealership.infrastracture.adapters;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.matvey.dealership.abstraction.*;

@Component
@Getter
@RequiredArgsConstructor

public class PersistanceRepositoryAdapter implements PersistanceRepository {
    private final CarsRepository carRepository;
    private final DetailsRepository detailsRepository;
    private final StockCarOrderRepository stockCarOrderRepository;
    private final TestDriveRepository testDriveRepository;
    private final UserRepository userRepository;
    private final ComplictaionOrderRepository complictaionOrderRepository;
}