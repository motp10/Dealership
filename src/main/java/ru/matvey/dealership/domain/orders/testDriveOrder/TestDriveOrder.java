package ru.matvey.dealership.domain.orders.testDriveOrder;

import lombok.Getter;
import ru.matvey.dealership.domain.orders.IOrder;
import ru.matvey.dealership.domain.orders.IStatus;
import ru.matvey.dealership.domain.orders.stockCarOrder.status.StockCarOrderCreated;
import ru.matvey.dealership.domain.orders.testDriveOrder.status.TestDriveOrderReady;
import ru.matvey.dealership.domain.users.ClientUser;
import ru.matvey.dealership.domain.users.DealeshipManagerUser;
import ru.matvey.dealership.domain.car.Car;

import java.util.UUID;
@Getter

public class TestDriveOrder implements IOrder {
    private final UUID id = UUID.randomUUID();
    private final UUID clientId;
    private final UUID managerId;
    private final UUID carId;
    private IStatus status;

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