package ru.matvey.dealership.domain.orders.testDriveOrder;

import lombok.Builder;
import lombok.Getter;
import ru.matvey.dealership.domain.orders.Order;
import ru.matvey.dealership.domain.orders.Status;
import ru.matvey.dealership.domain.orders.stockCarOrder.status.StockCarOrderCreated;

import java.util.UUID;

@Builder
public class TestDriveOrder implements Order {
    private final UUID id;
    private final UUID clientId;
    private final UUID managerId;
    private final UUID carId;
    private Status status;

    public TestDriveOrder(UUID id, UUID clientID, UUID carId, UUID managerId, Status status) {
        this.id = id;
        this.clientId = clientID;
        this.carId = carId;
        this.managerId = managerId;
        this.status = status;
    }

    public TestDriveOrder(UUID clientID, UUID carId, UUID managerId) {
        id = UUID.randomUUID();
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

    @Override
    public UUID getClientId() {
        return clientId;
    }

    @Override
    public UUID getManagerId() {
        return managerId;
    }

    @Override
    public UUID getCarId() {
        return carId;
    }

    @Override
    public String getStatus() {
        return status.getName();
    }
}