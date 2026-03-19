package ru.matvey.dealership.domain.orders.stockCarOrder;

import lombok.Builder;
import ru.matvey.dealership.domain.orders.Order;
import ru.matvey.dealership.domain.orders.Status;
import ru.matvey.dealership.domain.orders.stockCarOrder.status.StockCarOrderCreated;

import java.util.UUID;

@Builder
public class OrderStockCar implements Order {
    private UUID id;
    private UUID clientId;
    private UUID carId;
    private UUID managerId;
    private Status status;

    public OrderStockCar(UUID client, UUID car, UUID managerId) {
        id = UUID.randomUUID();
        this.clientId = client;
        this.carId = car;
        this.managerId = managerId;
        this.status = new StockCarOrderCreated();
    }

    public OrderStockCar(UUID id, UUID client, UUID car, UUID managerId, Status status) {
        this.id = id;
        this.clientId = client;
        this.carId = car;
        this.managerId = managerId;
        this.status = status;
    }

    public void nextStep() {
        status = status.next();
    }

    @Override
    public void cancel() {
        status = status.cancel();
    }

    public void cancelOrder() {
        status = status.cancel();
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