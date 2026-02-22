    package ru.matvey.dealership.Abstraction;

    import ru.matvey.dealership.domain.car.Car;
    import ru.matvey.dealership.domain.orders.testDriveOrder.TestDriveOrder;
    import java.util.List;
    import java.util.UUID;

    public interface TestDriveRepository {
        void addOrder(TestDriveOrder order);
        TestDriveOrder findById(UUID id);
        List<TestDriveOrder> getAll();
        void deleteById(UUID id);
    }