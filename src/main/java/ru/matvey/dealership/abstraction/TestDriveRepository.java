package ru.matvey.dealership.abstraction;

import ru.matvey.dealership.domain.orders.testDriveOrder.TestDriveOrder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestDriveRepository {
    void add(TestDriveOrder order);
    Optional<TestDriveOrder> findById(UUID id);
    List<TestDriveOrder> getAll();
}