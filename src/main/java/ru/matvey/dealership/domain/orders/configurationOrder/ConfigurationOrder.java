package ru.matvey.dealership.domain.orders.configurationOrder;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import ru.matvey.dealership.domain.car.Car;
import ru.matvey.dealership.domain.orders.Order;
import ru.matvey.dealership.domain.orders.Status;
import ru.matvey.dealership.domain.orders.stockCarOrder.status.StockCarOrderCreated;

@Entity
@Table(name = "complication_orders")
@Getter
public class ConfigurationOrder implements Order {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "client_id")
    private UUID clientId;

    @Column(name = "manager_id")
    private UUID managerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(name = "status")
    private String statusName;

    @Transient
    private Status status;

    @Column(name = "final_price")
    private Double finalPrice;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "removed")
    private boolean removed = false;

    protected ConfigurationOrder() {}

    public ConfigurationOrder(UUID clientId, Car car, UUID managerId) {
        this.id = UUID.randomUUID();
        this.clientId = clientId;
        this.car = car;
        this.managerId = managerId;
        this.status = new StockCarOrderCreated();
        this.statusName = status.getName();
        this.finalPrice = car.getTotalPrice();
    }

    public ConfigurationOrder(
        UUID id,
        UUID clientId,
        Car car,
        UUID managerId,
        Status status
    ) {
        this.id = id;
        this.clientId = clientId;
        this.car = car;
        this.managerId = managerId;
        this.status = status;
        this.statusName = status.getName();
        this.finalPrice = car.getTotalPrice();
    }

    @PostLoad
    protected void onLoad() {
        this.status = new ConfigurationOrderStatusFactory().Create(statusName);
    }

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
        if (status != null) statusName = status.getName();
        if (car != null) finalPrice = car.getTotalPrice();
    }

    @Override
    public void nextStep() {
        status = status.next();
        statusName = status.getName();
    }

    @Override
    public void cancel() {
        status = status.cancel();
        statusName = status.getName();
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
        return car.getId();
    }

    @Override
    public String getStatus() {
        return statusName;
    }
}
