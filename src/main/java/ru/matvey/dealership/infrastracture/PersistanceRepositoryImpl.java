package ru.matvey.dealership.infrastracture;

import lombok.Getter;
import ru.matvey.dealership.abstraction.*;

@Getter
public class PersistanceRepositoryImpl implements PersistanceRepository {
    private DetailsRepository detailsRepository;
    private UserRepository userRepository;
    private ComplictaionOrderRepository complictaionOrderRepository;
    private CarsRepository carsRepository;
    private StockCarOrderRepository stockCarOrderRepository;
    private TestDriveRepository testDriveRepository;
    public PersistanceRepositoryImpl(DetailsRepository detailsRepository,
                                     UserRepository managersRepository,
                                     ComplictaionOrderRepository complictaionOrderRepository,
                                     CarsRepository carsRepository,
                                     StockCarOrderRepository stockCarOrderRepository,
                                     TestDriveRepository testDriveRepository) {
        this.carsRepository = carsRepository;
        this.userRepository = managersRepository;
        this.complictaionOrderRepository = complictaionOrderRepository;
        this.detailsRepository = detailsRepository;
        this.testDriveRepository = testDriveRepository;
        this.stockCarOrderRepository = stockCarOrderRepository;
    }

    @Override
    public CarsRepository getCarRepository() {
        return carsRepository;
    }

    @Override
    public DetailsRepository getDetailsRepository() {
        return detailsRepository;
    }

    @Override
    public StockCarOrderRepository getStockCarOrderRepository() {
        return stockCarOrderRepository;
    }

    @Override
    public TestDriveRepository getTestDriveRepository() {
        return testDriveRepository;
    }

    @Override
    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Override
    public ComplictaionOrderRepository getComplictaionOrderRepository() {
        return complictaionOrderRepository;
    }
}
