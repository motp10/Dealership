package ru.matvey.dealership.infrastracture;

import ru.matvey.dealership.Abstraction.TestDriveRepository;
import ru.matvey.dealership.domain.orders.testDriveOrder.TestDriveOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTestDriveRepository implements TestDriveRepository {
    private final List<TestDriveOrder> storage = new ArrayList();

    @Override
    public void addOrder(TestDriveOrder order) {
        storage.add(order);
    }

    @Override
    public TestDriveOrder findById(UUID id) {
        return storage.stream().filter((x)->{return x.getId() == id; }).toList().getFirst();
    }

    @Override
    public List<TestDriveOrder> getAll() {
        return storage;
    }

    @Override
    public void deleteById(UUID id) {
        TestDriveOrder orderToDelete = storage.stream().filter((x)->{return x.getId() == id; }).toList().getFirst();
        storage.remove(orderToDelete);
    }
}
