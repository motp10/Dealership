package ru.matvey.dealership.Abstraction;

import ru.matvey.dealership.contracts.Models.DetailDto;

public interface PersistanceRepository {
    CarsRepository getCarRepository();
    DetailsRepository getDetailsRepository();
    StockCarOrderRepository getStockCarOrderRepository();
    TestDriveRepository getTestDriveRepository();
    ManagersRepository getManagerRepository();
    ComplictaionOrderRepository getComplictaionOrderRepository();
}
