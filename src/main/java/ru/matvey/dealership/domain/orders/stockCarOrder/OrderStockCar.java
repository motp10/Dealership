package ru.matvey.dealership.domain.orders.stockCarOrder;

import ru.matvey.dealership.domain.orders.Order;
import ru.matvey.dealership.domain.orders.Status;
import ru.matvey.dealership.domain.orders.stockCarOrder.status.StockCarOrderCreated;

import java.util.UUID;


public class OrderStockCar implements Order {
    UUID id = UUID.randomUUID();
    private final UUID clientId;
    private final UUID carId;
    private final UUID managerId;
    private Status orderStatus;

    public OrderStockCar(UUID client, UUID car, UUID managerId) {
        this.clientId = client;
        this.carId = car;
        this.managerId = managerId;
        this.orderStatus = new StockCarOrderCreated();
    }

    public void nextStep() {
        orderStatus = orderStatus.next();
    }

    @Override
    public void cancel() {
        orderStatus = orderStatus.cancel();
    }

    public void cancelOrder() {
        orderStatus = orderStatus.cancel();
    }

    @Override
    public UUID getId() {
        return id;
    }
}