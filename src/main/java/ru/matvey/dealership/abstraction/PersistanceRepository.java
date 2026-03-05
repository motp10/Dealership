package ru.matvey.dealership.abstraction;

public interface PersistanceRepository {
    CarsRepository getCarRepository();
    DetailsRepository getDetailsRepository();
    StockCarOrderRepository getStockCarOrderRepository();
    TestDriveRepository getTestDriveRepository();
    ManagersRepository getManagerRepository();
    ComplictaionOrderRepository getComplictaionOrderRepository();
}
