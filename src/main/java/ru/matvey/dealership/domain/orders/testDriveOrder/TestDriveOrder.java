package ru.matvey.dealership.domain.orders.testDriveOrder;

import lombok.Getter;
import ru.matvey.dealership.domain.orders.Order;
import ru.matvey.dealership.domain.orders.Status;
import ru.matvey.dealership.domain.orders.stockCarOrder.status.StockCarOrderCreated;

import java.util.UUID;
@Getter

public class TestDriveOrder implements Order {
    private final UUID id = UUID.randomUUID();
    private final UUID clientId;
    private final UUID managerId;
    private final UUID carId;
    private Status status;

    public TestDriveOrder(UUID clientID, UUID carId, UUID managerId) {
        this.clientId = clientID;
        this.carId = carId;
        this.managerId = managerId;
        this.status = new StockCarOrderCreated();
    }
    @Override
    public void nextStep() {
        this.status = this.status.next();
    }
    @Override
    public void cancel() {
        this.status = this.status.cancel();
    }

    @Override
    public UUID getId() {
        return id;
    }
}