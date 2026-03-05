package ru.matvey.dealership.domain.orders.configurationOrder;

import lombok.Getter;
import ru.matvey.dealership.domain.orders.Order;
import ru.matvey.dealership.domain.orders.Status;
import ru.matvey.dealership.domain.orders.stockCarOrder.status.StockCarOrderCreated;
import ru.matvey.dealership.domain.car.Car;

import java.util.UUID;

@Getter
public class ConfigurationOrder implements Order {
    UUID id = UUID.randomUUID();
    private final UUID clientId;
    private final Car car;
    private final UUID managerId;
    private Status orderStatus;

    public ConfigurationOrder(UUID client, Car car, UUID managerId) {
        this.clientId = client;
        this.car = car;
        this.managerId = managerId;
        this.orderStatus = new StockCarOrderCreated();
    }

    @Override
    public void nextStep() {
        orderStatus = orderStatus.next();
    }

    @Override
    public void cancel() {
        orderStatus = orderStatus.cancel();
    }

    @Override
    public UUID getId() {
        return id;
    }
}
