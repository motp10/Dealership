package ru.matvey.dealership.domain.orders.configurationOrder;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import ru.matvey.dealership.domain.details.Body.Body;
import ru.matvey.dealership.domain.details.Cost;
import ru.matvey.dealership.domain.details.Transmission.Transmission;
import ru.matvey.dealership.domain.details.color.CarPaint;
import ru.matvey.dealership.domain.details.drivelayout.DriveLayout;
import ru.matvey.dealership.domain.details.fuel.Fuel;
import ru.matvey.dealership.domain.exceptions.DomainValidationException;
import ru.matvey.dealership.domain.orders.Order;
import ru.matvey.dealership.domain.orders.Status;
import ru.matvey.dealership.domain.orders.stockCarOrder.status.StockCarOrderCreated;
import ru.matvey.dealership.domain.car.Car;

import java.util.UUID;

@Getter
@Builder
public class ConfigurationOrder implements Order {
    UUID id;
    private UUID clientId;
    private Car car;
    private UUID managerId;
    private Status status;

    public ConfigurationOrder(UUID client, Car car, UUID managerId) {
        id = UUID.randomUUID();
        this.clientId = client;
        this.car = car;
        this.managerId = managerId;
        this.status = new StockCarOrderCreated();
    }

    public ConfigurationOrder(UUID id,
               UUID client,
               Car car,
               UUID managerId,
               Status status) {
        this.id = id;
        this.clientId = client;
        this.car = car;
        this.managerId = managerId;
        this.status = status;
    }

    @Override
    public void nextStep() {
        status = status.next();
    }

    @Override
    public void cancel() {
        status = status.cancel();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public UUID getCarId() {
        return car.getId();
    }

    @Override
    public String getStatus() {
        return status.getName();
    }
}
