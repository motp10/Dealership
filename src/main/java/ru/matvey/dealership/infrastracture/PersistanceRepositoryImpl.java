package ru.matvey.dealership.infrastracture;

import lombok.Getter;
import ru.matvey.dealership.Abstraction.*;
import ru.matvey.dealership.contracts.Operations.CarComplictation;

@Getter
public class PersistanceRepositoryImpl implements PersistanceRepository {
    private DetailsRepository detailsRepository;
    private ManagersRepository managersRepository;
    private ComplictaionOrderRepository complictaionOrderRepository;
    private CarsRepository carsRepository;
    private StockCarOrderRepository stockCarOrderRepository;
    private TestDriveRepository testDriveRepository;
    public PersistanceRepositoryImpl(DetailsRepository detailsRepository,
                                     ManagersRepository managersRepository,
                                     ComplictaionOrderRepository complictaionOrderRepository,
                                     CarsRepository carsRepository,
                                     StockCarOrderRepository stockCarOrderRepository,
                                     TestDriveRepository testDriveRepository) {
        this.carsRepository = carsRepository;
        this.managersRepository = managersRepository;
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
    public ManagersRepository getManagerRepository() {
        return managersRepository;
    }

    @Override
    public ComplictaionOrderRepository getComplictaionOrderRepository() {
        return complictaionOrderRepository;
    }
}
