package ru.matvey.dealership.abstraction;

public interface PersistanceRepository {
    CarsRepository getCarRepository();
    DetailsRepository getDetailsRepository();
    StockCarOrderRepository getStockCarOrderRepository();
    TestDriveRepository getTestDriveRepository();
    UserRepository getUserRepository();
    ComplictaionOrderRepository getComplictaionOrderRepository();
}
